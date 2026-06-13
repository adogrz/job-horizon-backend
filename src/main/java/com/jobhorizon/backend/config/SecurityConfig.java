package com.jobhorizon.backend.config;

import com.jobhorizon.backend.seguridad.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración central de Spring Security.
 *
 * <p>Establece una política de seguridad stateless basada en JWT:
 * <ul>
 *   <li>CSRF deshabilitado (API REST con Bearer tokens)</li>
 *   <li>Sesiones stateless — sin HttpSession</li>
 *   <li>CORS configurado desde {@link CorsProperties}</li>
 *   <li>Rutas públicas: {@code /auth/**} y {@code /actuator/health}</li>
 *   <li>Todas las demás rutas requieren autenticación</li>
 *   <li>Autorización por privilegios mediante {@code @PreAuthorize} habilitada</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CorsProperties corsProperties;

    /**
     * @param jwtAuthFilter   filtro JWT que extrae y valida el Bearer token
     * @param corsProperties  propiedades de CORS leídas desde {@code application.yml}
     */
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, CorsProperties corsProperties) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.corsProperties = corsProperties;
    }

    /**
     * Define la cadena de filtros de seguridad HTTP.
     *
     * @param http objeto de configuración de Spring Security
     * @return cadena de filtros configurada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                .referrerPolicy(rp -> rp.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER))
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST,
                    "/auth/login",
                    "/auth/registro/postulante",
                    "/auth/registro/empresa",
                    "/auth/solicitar-desbloqueo",
                    "/auth/desbloquear"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/catalogos/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura CORS desde las propiedades del archivo de configuración.
     * Nunca se usa {@code *} para los orígenes permitidos.
     *
     * @return fuente de configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(corsProperties.allowedOrigins().split(",")));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Bean de codificación de contraseñas usando BCrypt con factor de costo 12.
     *
     * @return encoder BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Expone el {@link AuthenticationManager} como bean para ser inyectado en servicios.
     *
     * @param config configuración de autenticación de Spring
     * @return gestor de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
