package com.jobhorizon.backend.seguridad.jwt;

import com.jobhorizon.backend.config.JwtProperties;
import com.jobhorizon.backend.seguridad.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Servicio responsable de la generación y validación de JSON Web Tokens (JWT).
 *
 * <p>Los tokens se firman con HMAC-SHA256 usando el secreto configurado en
 * {@link JwtProperties}. Cada token incluye los claims:
 * <ul>
 *   <li>{@code sub} — correo del usuario (subject)</li>
 *   <li>{@code userId} — ID numérico del usuario</li>
 *   <li>{@code privilegios} — lista de claves de privilegios (usadas en {@code @PreAuthorize})</li>
 *   <li>{@code roles} — lista de nombres de roles del usuario</li>
 * </ul>
 * </p>
 */
@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_PRIVILEGIOS = "privilegios";
    private static final String CLAIM_ROLES = "roles";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    /**
     * Inicializa el servicio derivando la clave HMAC-SHA256 desde las propiedades.
     *
     * @param jwtProperties propiedades JWT leídas desde {@code application.yml}
     */
    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un JWT firmado para el usuario dado con sus privilegios activos.
     *
     * @param usuario    entidad del usuario autenticado
     * @param privilegios lista de claves de privilegios asignados al usuario (ej: {@code GESTIONAR_USUARIOS})
     * @param roles      lista de nombres de roles del usuario (ej: {@code ADMIN})
     * @return token JWT compacto y firmado
     */
    public String generarToken(Usuario usuario, List<String> privilegios, List<String> roles) {
        Instant now = Instant.now();
        Instant expiration = now.plus(jwtProperties.expirationHours(), ChronoUnit.HOURS);

        return Jwts.builder()
                .subject(usuario.getCorreo())
                .claim(CLAIM_USER_ID, usuario.getId())
                .claim(CLAIM_PRIVILEGIOS, privilegios)
                .claim(CLAIM_ROLES, roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extrae el correo electrónico (subject) de un token JWT.
     *
     * @param token JWT compacto y firmado
     * @return correo del usuario extraído del subject
     * @throws JwtException si el token es inválido o ha expirado
     */
    public String extraerCorreo(String token) {
        return parsearClaims(token).getSubject();
    }

    /**
     * Extrae la lista de privilegios embebidos en el JWT.
     *
     * @param token JWT compacto y firmado
     * @return lista de claves de privilegios (puede ser vacía, nunca {@code null})
     */
    @SuppressWarnings("unchecked")
    public List<String> extraerPrivilegios(String token) {
        Object raw = parsearClaims(token).get(CLAIM_PRIVILEGIOS);
        if (raw instanceof List<?> list) {
            return (List<String>) list;
        }
        return List.of();
    }

    /**
     * Valida que el token JWT sea auténtico y no haya expirado.
     *
     * @param token JWT compacto a validar
     * @return {@code true} si el token es válido y vigente, {@code false} en caso contrario
     */
    public boolean validarToken(String token) {
        try {
            parsearClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("Token JWT inválido: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * Parsea y verifica la firma del token, retornando sus claims.
     *
     * @param token JWT a parsear
     * @return claims del token
     * @throws JwtException si la firma es inválida o el token ha expirado
     */
    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
