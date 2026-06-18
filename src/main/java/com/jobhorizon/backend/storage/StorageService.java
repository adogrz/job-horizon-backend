package com.jobhorizon.backend.storage;

import com.jobhorizon.backend.config.R2Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.time.Duration;

/**
 * Servicio para gestionar la subida y eliminación de archivos utilizando Cloudflare R2 / S3.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final R2Properties r2Properties;

    /**
     * Genera una URL pre-firmada válida para realizar una petición HTTP PUT de subida de archivo.
     *
     * @param objectKey   la llave/ruta del archivo en el bucket
     * @param contentType el tipo MIME del archivo (ej. image/jpeg)
     * @return URL pre-firmada en formato de texto
     */
    public String generarPresignedPutUrl(String objectKey, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(r2Properties.bucketName())
                .key(objectKey)
                .contentType(contentType)
                .overrideConfiguration(AwsRequestOverrideConfiguration.builder()
                        .putRawQueryParameter("x-amz-content-sha256", "UNSIGNED-PAYLOAD")
                        .build())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .putObjectRequest(putObjectRequest)
                .build();

        String presignedUrl = s3Presigner.presignPutObject(presignRequest).url().toString();
        log.info("URL pre-firmada generada con éxito para la llave: {}", objectKey);
        return presignedUrl;
    }

    /**
     * Genera la URL pública que corresponderá a un objeto en el bucket.
     *
     * @param objectKey la llave/ruta del archivo en el bucket
     * @return la URL pública final
     */
    public String obtenerPublicUrl(String objectKey) {
        String baseUrl = r2Properties.publicUrl();
        if (baseUrl.endsWith("/")) {
            return baseUrl + objectKey;
        }
        return baseUrl + "/" + objectKey;
    }

    /**
     * Elimina físicamente un objeto del bucket R2.
     *
     * @param objectKey la llave/ruta del archivo en el bucket
     */
    public void deleteFile(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            return;
        }
        try {
            log.info("Intentando eliminar archivo de R2 con llave: {}", objectKey);
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(r2Properties.bucketName())
                    .key(objectKey)
                    .build();
            s3Client.deleteObject(deleteRequest);
            log.info("Archivo eliminado con éxito de R2 con llave: {}", objectKey);
        } catch (Exception e) {
            log.error("Error al eliminar el archivo {} de R2: {}", objectKey, e.getMessage(), e);
        }
    }

    /**
     * Extrae el objectKey (llave del objeto) a partir de una URL pública completa.
     *
     * @param publicUrl URL pública completa (ej: https://pub-xxx.r2.dev/postulantes/5/foto.jpg)
     * @return llave del objeto (ej: postulantes/5/foto.jpg) o null si la URL es inválida
     */
    public String extraerObjectKey(String publicUrl) {
        if (publicUrl == null || publicUrl.isBlank()) {
            return null;
        }
        String prefix = r2Properties.publicUrl();
        if (!prefix.endsWith("/")) {
            prefix += "/";
        }
        if (publicUrl.startsWith(prefix)) {
            return publicUrl.substring(prefix.length());
        }
        // Fallback robusto usando URI java.net
        try {
            URI uri = new URI(publicUrl);
            String path = uri.getPath();
            if (path != null && path.startsWith("/")) {
                return path.substring(1);
            }
            return path;
        } catch (Exception e) {
            log.warn("No se pudo parsear como URI el publicUrl: {}, se usará valor crudo", publicUrl);
            return publicUrl;
        }
    }

    /**
     * Genera la llave/ruta determinista para la foto de perfil de un postulante.
     */
    public String generarLlavePostulanteFoto(Integer idUsuario, String extension) {
        return "postulantes/" + idUsuario + "/foto_perfil." + limpiarExtension(extension);
    }

    /**
     * Genera la llave/ruta determinista para el CV en PDF de un postulante.
     */
    public String generarLlavePostulanteCv(Integer idUsuario, String extension) {
        return "postulantes/" + idUsuario + "/cv." + limpiarExtension(extension);
    }

    /**
     * Genera la llave/ruta determinista para el logo de una empresa.
     */
    public String generarLlaveEmpresaLogo(Integer idUsuario, String extension) {
        return "empresas/" + idUsuario + "/logo." + limpiarExtension(extension);
    }

    private String limpiarExtension(String extension) {
        if (extension == null || extension.isBlank()) {
            return "bin";
        }
        String limpia = extension.toLowerCase().trim();
        if (limpia.startsWith(".")) {
            limpia = limpia.substring(1);
        }
        return limpia;
    }
}
