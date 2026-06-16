package com.jobhorizon.backend.correo;

import com.jobhorizon.backend.config.FrontendProperties;
import com.jobhorizon.backend.config.ResendProperties;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CorreoService {

    private final Resend resend;
    private final ResendProperties resendProperties;
    private final FrontendProperties frontendProperties;

    public CorreoService(Resend resend, ResendProperties resendProperties, FrontendProperties frontendProperties) {
        this.resend = resend;
        this.resendProperties = resendProperties;
        this.frontendProperties = frontendProperties;
    }

    public void enviarCorreoDesbloqueo(String destinatario, String token) {
        String linkDesbloqueo = frontendProperties.baseUrl() + "/desbloquear?token=" + token;
        
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #333333;
                        background-color: #f4f6f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 40px auto;
                        background: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
                    }
                    .header {
                        background-color: #1e293b;
                        color: #ffffff;
                        padding: 30px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                        font-weight: 600;
                    }
                    .content {
                        padding: 40px 30px;
                    }
                    .btn {
                        display: inline-block;
                        background-color: #2563eb;
                        color: #ffffff !important;
                        padding: 12px 24px;
                        text-decoration: none;
                        border-radius: 5px;
                        font-weight: bold;
                        margin-top: 20px;
                        text-align: center;
                    }
                    .footer {
                        background-color: #f8fafc;
                        padding: 20px;
                        text-align: center;
                        font-size: 12px;
                        color: #64748b;
                        border-top: 1px solid #e2e8f0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>JobHorizon</h1>
                    </div>
                    <div class="content">
                        <h2>Cuenta Bloqueada</h2>
                        <p>Hemos detectado varios intentos fallidos de inicio de sesión en tu cuenta. Por motivos de seguridad, tu cuenta ha sido bloqueada temporalmente.</p>
                        <p>Para desbloquear tu cuenta y volver a acceder a JobHorizon, por favor haz clic en el siguiente enlace:</p>
                        <div style="text-align: center;">
                            <a href="%s" class="btn">Desbloquear Mi Cuenta</a>
                        </div>
                        <p style="margin-top: 30px; font-size: 14px; color: #64748b;">
                            Si el botón no funciona, puedes copiar y pegar la siguiente URL en tu navegador:
                            <br/>
                            <a href="%s">%s</a>
                        </p>
                        <p>Este enlace expirará en las próximas 24 horas.</p>
                        <p>Si tú no solicitaste este desbloqueo o no has intentado iniciar sesión, por favor ignora este correo o ponte en contacto con soporte.</p>
                    </div>
                    <div class="footer">
                        &copy; 2026 JobHorizon. Todos los derechos reservados.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(linkDesbloqueo, linkDesbloqueo, linkDesbloqueo);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(resendProperties.fromEmail())
                .to(destinatario)
                .subject("Desbloqueo de cuenta - JobHorizon")
                .html(htmlContent)
                .build();

        try {
            log.info("Enviando correo de desbloqueo a {}", destinatario);
            resend.emails().send(params);
            log.info("Correo de desbloqueo enviado con éxito a {}", destinatario);
        } catch (ResendException e) {
            log.error("Error al enviar el correo de desbloqueo a {}: {}", destinatario, e.getMessage(), e);
            throw new RuntimeException("No se pudo enviar el correo de desbloqueo", e);
        }
    }

    public void enviarCorreoNotificacionOfertaCerrada(String destinatario, String tituloOferta, String nombreEmpresa) {
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #333333;
                        background-color: #f4f6f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 40px auto;
                        background: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
                    }
                    .header {
                        background-color: #1e293b;
                        color: #ffffff;
                        padding: 30px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                        font-weight: 600;
                    }
                    .content {
                        padding: 40px 30px;
                    }
                    .footer {
                        background-color: #f8fafc;
                        padding: 20px;
                        text-align: center;
                        font-size: 12px;
                        color: #64748b;
                        border-top: 1px solid #e2e8f0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>JobHorizon</h1>
                    </div>
                    <div class="content">
                        <h2>Notificación de Proceso Finalizado</h2>
                        <p>Te escribimos para informarte que el proceso de selección para la vacante de <strong>%s</strong> en la empresa <strong>%s</strong> a la que aplicaste ha sido cerrado.</p>
                        <p>Queremos agradecerte el tiempo y esfuerzo dedicados al postularte a esta oportunidad. Aunque en esta ocasión el proceso ha finalizado, tu perfil seguirá disponible en nuestra plataforma para futuras oportunidades que se adapten a tu perfil.</p>
                        <p>Te deseamos el mayor de los éxitos en tu búsqueda de empleo y desarrollo profesional.</p>
                        <p>Atentamente,<br/>El equipo de JobHorizon</p>
                    </div>
                    <div class="footer">
                        &copy; 2026 JobHorizon. Todos los derechos reservados.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(tituloOferta, nombreEmpresa);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(resendProperties.fromEmail())
                .to(destinatario)
                .subject("Actualización de postulación: " + tituloOferta + " - JobHorizon")
                .html(htmlContent)
                .build();

        try {
            log.info("Enviando correo de finalización de oferta a {}", destinatario);
            resend.emails().send(params);
            log.info("Correo de finalización de oferta enviado con éxito a {}", destinatario);
        } catch (ResendException e) {
            log.error("Error al enviar el correo de finalización de oferta a {}: {}", destinatario, e.getMessage(), e);
            // No lanzamos excepción para evitar que el cambio de estado de la oferta falle por problemas de correo
        }
    }

    public void enviarCorreoConfirmacionPostulacion(String destinatario, String tituloOferta, String nombreEmpresa) {
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #333333;
                        background-color: #f4f6f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 40px auto;
                        background: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
                    }
                    .header {
                        background-color: #1e293b;
                        color: #ffffff;
                        padding: 30px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                        font-weight: 600;
                    }
                    .content {
                        padding: 40px 30px;
                    }
                    .highlight {
                        background-color: #eff6ff;
                        border-left: 4px solid #2563eb;
                        padding: 15px;
                        margin: 20px 0;
                        border-radius: 0 4px 4px 0;
                    }
                    .footer {
                        background-color: #f8fafc;
                        padding: 20px;
                        text-align: center;
                        font-size: 12px;
                        color: #64748b;
                        border-top: 1px solid #e2e8f0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>JobHorizon</h1>
                    </div>
                    <div class="content">
                        <h2>¡Postulación Recibida!</h2>
                        <p>Te confirmamos que te has postulado con éxito a la siguiente vacante:</p>
                        <div class="highlight">
                            <strong>Puesto:</strong> %s<br/>
                            <strong>Empresa:</strong> %s
                        </div>
                        <p>La empresa revisará tu perfil y se pondrá en contacto contigo si cumples con los requerimientos del puesto. Podrás dar seguimiento al estado de tu postulación desde tu cuenta en JobHorizon.</p>
                        <p>¡Mucho éxito en tu proceso de selección!</p>
                        <p>Atentamente,<br/>El equipo de JobHorizon</p>
                    </div>
                    <div class="footer">
                        &copy; 2026 JobHorizon. Todos los derechos reservados.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(tituloOferta, nombreEmpresa);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(resendProperties.fromEmail())
                .to(destinatario)
                .subject("Confirmación de postulación: " + tituloOferta + " - JobHorizon")
                .html(htmlContent)
                .build();

        try {
            log.info("Enviando correo de confirmación de postulación a {}", destinatario);
            resend.emails().send(params);
            log.info("Correo de confirmación de postulación enviado con éxito a {}", destinatario);
        } catch (ResendException e) {
            log.error("Error al enviar el correo de confirmación de postulación a {}: {}", destinatario, e.getMessage(), e);
        }
    }

    public void enviarCorreoCambioEstadoPostulacion(String destinatario, String tituloOferta, String nombreEmpresa, String nuevoEstado) {
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #333333;
                        background-color: #f4f6f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 40px auto;
                        background: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
                    }
                    .header {
                        background-color: #1e293b;
                        color: #ffffff;
                        padding: 30px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                        font-weight: 600;
                    }
                    .content {
                        padding: 40px 30px;
                    }
                    .highlight {
                        background-color: #f0fdf4;
                        border-left: 4px solid #16a34a;
                        padding: 15px;
                        margin: 20px 0;
                        border-radius: 0 4px 4px 0;
                    }
                    .footer {
                        background-color: #f8fafc;
                        padding: 20px;
                        text-align: center;
                        font-size: 12px;
                        color: #64748b;
                        border-top: 1px solid #e2e8f0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>JobHorizon</h1>
                    </div>
                    <div class="content">
                        <h2>Actualización de tu Postulación</h2>
                        <p>Tu postulación para la vacante de <strong>%s</strong> en <strong>%s</strong> ha cambiado de estado:</p>
                        <div class="highlight">
                            <strong>Nuevo Estado:</strong> %s
                        </div>
                        <p>Ingresa a tu cuenta de JobHorizon para ver más detalles sobre este proceso o estar al tanto de posibles mensajes de la empresa.</p>
                        <p>Atentamente,<br/>El equipo de JobHorizon</p>
                    </div>
                    <div class="footer">
                        &copy; 2026 JobHorizon. Todos los derechos reservados.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(tituloOferta, nombreEmpresa, nuevoEstado);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(resendProperties.fromEmail())
                .to(destinatario)
                .subject("Actualización de tu postulación: " + tituloOferta + " - JobHorizon")
                .html(htmlContent)
                .build();

        try {
            log.info("Enviando correo de actualización de postulación a {}", destinatario);
            resend.emails().send(params);
            log.info("Correo de actualización de postulación enviado con éxito a {}", destinatario);
        } catch (ResendException e) {
            log.error("Error al enviar el correo de actualización de postulación a {}: {}", destinatario, e.getMessage(), e);
        }
    }
}
