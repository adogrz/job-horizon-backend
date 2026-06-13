package com.jobhorizon.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de la documentación OpenAPI (Swagger UI).
 *
 * <p>Acceder a la interfaz en: {@code /swagger-ui.html}</p>
 * <p>Para endpoints protegidos, hacer clic en "Authorize" e ingresar el token JWT.</p>
 */
@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI jobHorizonOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JobHorizon API")
                        .description("""
                                API REST de la plataforma **JobHorizon** para la gestión de postulantes, empresas y ofertas laborales.
                                
                                ## Autenticación
                                La mayoría de endpoints requieren un token JWT. Para autenticarte:
                                1. Llama a `POST /auth/login` con tus credenciales.
                                2. Copia el valor del campo `data.token` de la respuesta.
                                3. Haz clic en el botón **Authorize** (🔒) e ingresa: `Bearer <tu_token>`.
                                
                                ## Formato de respuesta
                                Todos los endpoints retornan la misma estructura:
                                ```json
                                {
                                  "success": true,
                                  "message": "Mensaje de la operación",
                                  "data": { ... },
                                  "timestamp": "2025-01-01T12:00:00"
                                }
                                ```
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo JobHorizon")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                .name(BEARER_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingresa el token JWT obtenido del endpoint /auth/login")));
    }
}
