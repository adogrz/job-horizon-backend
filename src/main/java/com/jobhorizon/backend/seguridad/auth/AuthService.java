package com.jobhorizon.backend.seguridad.auth;

import com.jobhorizon.backend.correo.CorreoService;
import com.jobhorizon.backend.distrito.Distrito;
import com.jobhorizon.backend.distrito.DistritoRepository;
import com.jobhorizon.backend.empresa.Empresa;
import com.jobhorizon.backend.empresa.EmpresaRepository;
import com.jobhorizon.backend.genero.Genero;
import com.jobhorizon.backend.genero.GeneroRepository;
import com.jobhorizon.backend.postulante.Postulante;
import com.jobhorizon.backend.postulante.PostulanteRepository;
import com.jobhorizon.backend.seguridad.auth.dto.*;
import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuario;
import com.jobhorizon.backend.seguridad.estadousuario.EstadoUsuarioRepository;
import com.jobhorizon.backend.seguridad.exception.*;
import com.jobhorizon.backend.seguridad.jwt.JwtService;
import com.jobhorizon.backend.seguridad.rol.Rol;
import com.jobhorizon.backend.seguridad.rol.RolRepository;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import com.jobhorizon.backend.seguridad.usuario.UsuarioRepository;
import com.jobhorizon.backend.tipodocumento.TipoDocumento;
import com.jobhorizon.backend.tipodocumento.TipoDocumentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final RolRepository rolRepository;
    private final PostulanteRepository postulanteRepository;
    private final EmpresaRepository empresaRepository;
    private final GeneroRepository generoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DistritoRepository distritoRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CorreoService correoService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(noRollbackFor = {CredencialesInvalidasException.class, CuentaBloqueadaException.class})
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales incorrectas"));

        String estado = usuario.getEstadoUsuario().getNombre();
        if ("BLOQUEADO".equals(estado)) {
            throw new CuentaBloqueadaException("La cuenta está bloqueada. Por favor, solicite su desbloqueo.");
        }
        if ("INACTIVO".equals(estado)) {
            throw new CuentaInactivaException("La cuenta está inactiva.");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            usuarioRepository.registrarIntentoFallido(usuario.getId());

            // Force JPA to reload the user entity from the database to see updates from the SP
            entityManager.refresh(usuario);

            if ("BLOQUEADO".equals(usuario.getEstadoUsuario().getNombre())) {
                String token = UUID.randomUUID().toString();
                usuarioRepository.generarTokenDesbloqueo(usuario.getId(), token);
                
                // Flush the generated token so that it is persisted before throwing the exception
                usuarioRepository.flush();

                try {
                    correoService.enviarCorreoDesbloqueo(usuario.getCorreo(), token);
                } catch (Exception e) {
                    log.error("No se pudo enviar el correo de desbloqueo al usuario {}", usuario.getCorreo(), e);
                }
                throw new CuentaBloqueadaException("La cuenta ha sido bloqueada debido a demasiados intentos fallidos. Se ha enviado un correo con instrucciones para desbloquearla.");
            }

            throw new CredencialesInvalidasException("Credenciales incorrectas");
        }

        // Login successful
        usuarioRepository.resetearIntentosFallidos(usuario.getId());

        List<String> privilegios = usuarioRepository.obtenerNombresPrivilegios(usuario.getId());
        List<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .toList();

        String token = jwtService.generarToken(usuario, privilegios, roles);

        return new LoginResponse(token, usuario.getCorreo(), roles, privilegios);
    }

    @Transactional
    public LoginResponse registrarPostulante(RegistroPostulanteRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new CorreoDuplicadoException("El correo electrónico ya está registrado");
        }

        EstadoUsuario estadoActivo = estadoUsuarioRepository.findByNombre("ACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));

        Rol rolPostulante = rolRepository.findByNombre("POSTULANTE")
                .orElseThrow(() -> new RuntimeException("Rol POSTULANTE no encontrado"));

        Genero genero = generoRepository.findById(request.getIdGenero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado"));

        TipoDocumento tipoDoc = tipoDocumentoRepository.findById(request.getIdTipoDocumento())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento no encontrado"));

        Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RecursoNoEncontradoException("Distrito no encontrado"));

        Usuario usuario = Usuario.builder()
                .correo(request.getCorreo())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .intentosFallidos((byte) 0)
                .estadoUsuario(estadoActivo)
                .roles(Set.of(rolPostulante))
                .build();
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        Postulante postulante = Postulante.builder()
                .usuario(usuario)
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .fechaNacimiento(request.getFechaNacimiento())
                .numDocumento(request.getNumDocumento())
                .nup(request.getNup())
                .nit(request.getNit())
                .direccion(request.getDireccion())
                .genero(genero)
                .tipoDocumento(tipoDoc)
                .distrito(distrito)
                .build();
        postulanteRepository.save(postulante);

        List<String> privilegios = usuarioRepository.obtenerNombresPrivilegios(usuario.getId());
        List<String> roles = List.of(rolPostulante.getNombre());

        String token = jwtService.generarToken(usuario, privilegios, roles);

        return new LoginResponse(token, usuario.getCorreo(), roles, privilegios);
    }

    @Transactional
    public LoginResponse registrarEmpresa(RegistroEmpresaRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new CorreoDuplicadoException("El correo electrónico ya está registrado");
        }

        EstadoUsuario estadoActivo = estadoUsuarioRepository.findByNombre("ACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no encontrado"));

        Rol rolEmpresa = rolRepository.findByNombre("EMPRESA")
                .orElseThrow(() -> new RuntimeException("Rol EMPRESA no encontrado"));

        Distrito distrito = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RecursoNoEncontradoException("Distrito no encontrado"));

        Usuario usuario = Usuario.builder()
                .correo(request.getCorreo())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .intentosFallidos((byte) 0)
                .estadoUsuario(estadoActivo)
                .roles(Set.of(rolEmpresa))
                .build();
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        Empresa empresa = Empresa.builder()
                .usuario(usuario)
                .nombreComercial(request.getNombreComercial())
                .razonSocial(request.getRazonSocial())
                .nit(request.getNit())
                .sitioWeb(request.getSitioWeb())
                .descripcion(request.getDescripcion())
                .distrito(distrito)
                .build();
        empresaRepository.save(empresa);

        List<String> privilegios = usuarioRepository.obtenerNombresPrivilegios(usuario.getId());
        List<String> roles = List.of(rolEmpresa.getNombre());

        String token = jwtService.generarToken(usuario, privilegios, roles);

        return new LoginResponse(token, usuario.getCorreo(), roles, privilegios);
    }

    @Transactional
    public void solicitarDesbloqueo(SolicitudDesbloqueoRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!"BLOQUEADO".equals(usuario.getEstadoUsuario().getNombre())) {
            throw new IllegalArgumentException("La cuenta no se encuentra bloqueada.");
        }

        String token = UUID.randomUUID().toString();
        usuarioRepository.generarTokenDesbloqueo(usuario.getId(), token);
        correoService.enviarCorreoDesbloqueo(usuario.getCorreo(), token);
    }

    @Transactional
    public void desbloquear(DesbloqueoRequest request) {
        int resultado = usuarioRepository.desbloquearUsuario(request.getToken());

        switch (resultado) {
            case 0:
                log.info("Usuario desbloqueado con éxito para el token {}", request.getToken());
                break;
            case 1:
                throw new TokenInvalidoException("Token de desbloqueo inválido o no encontrado");
            case 2:
                throw new TokenExpiradoException("El token de desbloqueo ha expirado");
            default:
                throw new RuntimeException("Error inesperado al desbloquear el usuario");
        }
    }
}
