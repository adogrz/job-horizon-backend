package com.jobhorizon.backend;

import com.jobhorizon.backend.config.CorsProperties;
import com.jobhorizon.backend.config.FrontendProperties;
import com.jobhorizon.backend.config.JwtProperties;
import com.jobhorizon.backend.config.ResendProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Punto de entrada principal de la aplicación JobHorizon.
 *
 * <p>Habilita el binding de {@link JwtProperties}, {@link ResendProperties},
 * {@link CorsProperties} y {@link FrontendProperties} desde {@code application.yml}.</p>
 */
@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, ResendProperties.class, CorsProperties.class, FrontendProperties.class})
public class JobHorizonApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobHorizonApplication.class, args);
	}
}
