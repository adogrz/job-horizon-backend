package com.jobhorizon.backend.seguridad.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticación JWT que se ejecuta una sola vez por petición HTTP.
 *
 * <p>Extrae el token del header {@code Authorization: Bearer <token>}, lo valida
 * mediante {@link JwtService} y, si es válido, construye una autenticación con las
 * authorities derivadas de los privilegios embebidos en el token (sin consultar la BD).
 * La autenticación se registra en el {@code SecurityContextHolder} para que
 * Spring Security procese la autorización normalmente.</p>
 *
 * <p>Si el header no está presente o el token no es válido, el filtro continúa la cadena
 * sin setear autenticación — Spring Security rechazará la petición si el endpoint la requiere.</p>
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    /**
     * @param jwtService servicio de JWT para validación y extracción de claims
     */
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Procesa la petición extrayendo y validando el JWT.
     *
     * @param request     petición HTTP entrante
     * @param response    respuesta HTTP
     * @param filterChain cadena de filtros de Spring Security
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(BEARER_PREFIX.length());

            if (jwtService.validarToken(token)) {
                List<SimpleGrantedAuthority> authorities = jwtService.extraerPrivilegios(token)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                jwtService.extraerCorreo(token),
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
