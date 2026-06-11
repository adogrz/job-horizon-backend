package com.jobhorizon.backend.config;

import com.resend.Resend;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración del cliente Resend para el envío de correos transaccionales.
 *
 * <p>Expone un bean singleton de {@link Resend} inicializado con la API key
 * definida en {@link ResendProperties}.</p>
 */
@Configuration
public class ResendConfig {

    /**
     * Crea y configura el cliente de la SDK de Resend.
     *
     * @param properties propiedades de Resend leídas desde {@code application.yml}
     * @return cliente {@link Resend} listo para usar
     */
    @Bean
    public Resend resend(ResendProperties properties) {
        return new Resend(properties.apiKey());
    }
}
