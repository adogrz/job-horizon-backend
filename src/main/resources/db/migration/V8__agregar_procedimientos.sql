-- ------------------------------------------------------------
-- SP: sp_RegistrarIntentoFallido
-- Incrementa los intentos fallidos de login y bloquea al
-- usuario si supera el límite configurado en ConfiguracionSistema
-- (clave: MAX_INTENTOS_FALLIDOS, por defecto 3).
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_RegistrarIntentoFallido @IdUsuario INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @Intentos INT;
        DECLARE @IdBloqueado INT;
        DECLARE @MaxIntentos INT;

        -- Límite parametrizable desde ConfiguracionSistema
        SELECT @MaxIntentos = CAST(Valor AS INT)
        FROM ConfiguracionSistema
        WHERE Clave = 'MAX_INTENTOS_FALLIDOS';

        IF @MaxIntentos IS NULL SET @MaxIntentos = 3; -- valor de seguridad por defecto

        SELECT @Intentos = IntentosFallidos
        FROM Usuario
        WHERE IdUsuario = @IdUsuario;

        IF @Intentos IS NULL
            BEGIN
                RAISERROR ('Usuario con IdUsuario %d no encontrado.', 16, 1, @IdUsuario);
                RETURN;
            END

        SET @Intentos = @Intentos + 1;

        -- Obtener el ID del estado BLOQUEADO
        SELECT @IdBloqueado = IdEstadoUsuario
        FROM EstadoUsuario
        WHERE Nombre = 'BLOQUEADO';

        IF @Intentos >= @MaxIntentos
            BEGIN
                UPDATE Usuario
                SET IntentosFallidos = @Intentos,
                    IdEstadoUsuario  = @IdBloqueado
                WHERE IdUsuario = @IdUsuario;
            END
        ELSE
            BEGIN
                UPDATE Usuario
                SET IntentosFallidos = @Intentos
                WHERE IdUsuario = @IdUsuario;
            END

        SELECT IntentosFallidos, IdEstadoUsuario
        FROM Usuario
        WHERE IdUsuario = @IdUsuario;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_ResetearIntentosFallidos
-- Reinicia los intentos fallidos al hacer login exitoso.
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_ResetearIntentosFallidos @IdUsuario INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM Usuario WHERE IdUsuario = @IdUsuario)
            BEGIN
                RAISERROR ('Usuario con IdUsuario %d no encontrado.', 16, 1, @IdUsuario);
                RETURN;
            END

        UPDATE Usuario
        SET IntentosFallidos = 0
        WHERE IdUsuario = @IdUsuario;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_GenerarTokenDesbloqueo
-- Guarda el token de desbloqueo y su fecha de expiración.
-- El tiempo de expiración es parametrizable via ConfiguracionSistema
-- (clave: TOKEN_EXPIRACION_HORAS, por defecto 24).
-- Spring llama a este SP y luego envía el correo con el token.
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_GenerarTokenDesbloqueo @IdUsuario INT,
                                                    @Token VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @HorasExp INT;

        SELECT @HorasExp = CAST(Valor AS INT)
        FROM ConfiguracionSistema
        WHERE Clave = 'TOKEN_EXPIRACION_HORAS';

        IF @HorasExp IS NULL SET @HorasExp = 24; -- valor de seguridad por defecto

        IF NOT EXISTS (SELECT 1 FROM Usuario WHERE IdUsuario = @IdUsuario)
            BEGIN
                RAISERROR ('Usuario con IdUsuario %d no encontrado.', 16, 1, @IdUsuario);
                RETURN;
            END

        UPDATE Usuario
        SET TokenDesbloqueo = @Token,
            FechaTokenExp   = DATEADD(HOUR, @HorasExp, GETDATE())
        WHERE IdUsuario = @IdUsuario;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_DesbloquearUsuario
-- Valida el token y reactiva al usuario si no ha expirado.
-- Retorna mediante parámetro OUTPUT:
--   0 = OK
--   1 = token inválido o no encontrado
--   2 = token expirado
--  -1 = error inesperado (capturado en CATCH)
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_DesbloquearUsuario @Token VARCHAR(100),
                                                @Resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @IdUsuario INT;
        DECLARE @FechaExp DATETIME2;
        DECLARE @IdActivo INT;

        SELECT @IdUsuario = IdUsuario,
               @FechaExp = FechaTokenExp
        FROM Usuario
        WHERE TokenDesbloqueo = @Token;

        IF @IdUsuario IS NULL
            BEGIN
                SET @Resultado = 1; -- token inválido
                RETURN;
            END

        IF GETDATE() > @FechaExp
            BEGIN
                SET @Resultado = 2; -- token expirado
                RETURN;
            END

        SELECT @IdActivo = IdEstadoUsuario
        FROM EstadoUsuario
        WHERE Nombre = 'ACTIVO';

        UPDATE Usuario
        SET IdEstadoUsuario  = @IdActivo,
            IntentosFallidos = 0,
            TokenDesbloqueo  = NULL,
            FechaTokenExp    = NULL
        WHERE IdUsuario = @IdUsuario;

        SET @Resultado = 0; -- éxito
    END TRY
    BEGIN CATCH
        SET @Resultado = -1;
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_ObtenerPrivilegiosUsuario
-- Retorna todos los privilegios activos de un usuario
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_ObtenerPrivilegiosUsuario @IdUsuario INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        SELECT DISTINCT p.IdPrivilegio,
                        p.Nombre,
                        p.NombreMenu,
                        p.Ruta
        FROM UsuarioRol ur
                 JOIN RolPrivilegio rp ON ur.IdRol = rp.IdRol
                 JOIN Privilegio p ON rp.IdPrivilegio = p.IdPrivilegio
        WHERE ur.IdUsuario = @IdUsuario
        ORDER BY p.Nombre;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_ObtenerAspirantes
-- Núcleo del matching: retorna postulantes que cumplen
-- los requisitos mínimos de una oferta de trabajo.
-- Parámetros opcionales: cada filtro es NULL si no aplica.
-- El puntaje se calcula con fn_PuntajeMatching (4 variables).
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_ObtenerAspirantes @IdOferta INT,
                                               @IdDepartamento INT = NULL,
                                               @SoloDisponibles BIT = 1
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        -- Habilidades requeridas por la oferta
        DECLARE @HabilidadesRequeridas INT;
        SELECT @HabilidadesRequeridas = COUNT(*)
        FROM OfertaHabilidad
        WHERE IdOferta = @IdOferta;

        -- Idiomas requeridos por la oferta
        DECLARE @IdiomasRequeridos INT;
        SELECT @IdiomasRequeridos = COUNT(*)
        FROM OfertaIdioma
        WHERE IdOferta = @IdOferta;

        SELECT p.IdUsuario,
               p.Nombres,
               p.Apellidos,
               p.Nombres + ' ' + p.Apellidos                                                  AS NombreCompleto,
               u.Correo,
               dep.Nombre                                                                     AS Departamento,
               dis.Nombre                                                                     AS Distrito,

               -- Habilidades que coinciden (nivel mínimo cumplido)
               (SELECT COUNT(*)
                FROM PostulanteHabilidad ph
                         JOIN OfertaHabilidad oh
                              ON ph.IdHabilidad = oh.IdHabilidad
                                  AND oh.IdOferta = @IdOferta
                         JOIN NivelHabilidad nph ON ph.IdNivelHabilidad = nph.IdNivelHabilidad
                         JOIN NivelHabilidad noh ON oh.IdNivelHabilidad = noh.IdNivelHabilidad
                WHERE ph.IdUsuario = p.IdUsuario
                  AND nph.OrdenComparacion >= noh.OrdenComparacion)                           AS HabilidadesCoinciden,

               @HabilidadesRequeridas                                                         AS HabilidadesRequeridas,

               -- Idiomas que coinciden (mínimo en todas las 4 habilidades lingüísticas)
               (SELECT COUNT(*)
                FROM PostulanteIdioma pi2
                         JOIN OfertaIdioma oi
                              ON pi2.IdIdioma = oi.IdIdioma
                                  AND oi.IdOferta = @IdOferta
                         JOIN NivelIdioma nr ON oi.IdNivelIdioma = nr.IdNivelIdioma
                         JOIN NivelIdioma nLec ON pi2.IdNivelLectura = nLec.IdNivelIdioma
                         JOIN NivelIdioma nEsc ON pi2.IdNivelEscritura = nEsc.IdNivelIdioma
                         JOIN NivelIdioma nCon ON pi2.IdNivelConversacion = nCon.IdNivelIdioma
                         JOIN NivelIdioma nLis ON pi2.IdNivelEscucha = nLis.IdNivelIdioma
                WHERE pi2.IdUsuario = p.IdUsuario
                  AND (SELECT MIN(v)
                       FROM (VALUES (nLec.OrdenComparacion),
                                    (nEsc.OrdenComparacion),
                                    (nCon.OrdenComparacion),
                                    (nLis.OrdenComparacion)) AS T(v)) >= nr.OrdenComparacion) AS IdiomasCoinciden,

               @IdiomasRequeridos                                                             AS IdiomasRequeridos,

               -- Puntaje de matching completo (4 variables ponderadas)
               dbo.fn_PuntajeMatching(p.IdUsuario, @IdOferta)                                 AS PuntajeMatching

        FROM Postulante p
                 JOIN Usuario u ON p.IdUsuario = u.IdUsuario
                 JOIN Distrito dis ON p.IdDistrito = dis.IdDistrito
                 JOIN Departamento dep ON dis.IdDepartamento = dep.IdDepartamento
                 JOIN EstadoUsuario eu ON u.IdEstadoUsuario = eu.IdEstadoUsuario

        WHERE eu.Nombre = 'ACTIVO'
          AND (@IdDepartamento IS NULL OR dep.IdDepartamento = @IdDepartamento)
          -- Solo postulantes que no han aplicado ya a esta oferta
          AND NOT EXISTS (SELECT 1
                          FROM PostulanteOferta po
                          WHERE po.IdUsuario = p.IdUsuario
                            AND po.IdOferta = @IdOferta)

        ORDER BY PuntajeMatching DESC;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_AplicarOferta
-- Registra la postulación de un candidato a una oferta.
-- Valida duplicados y que la oferta esté activa.
-- Retorna: 0 = OK, 1 = ya aplicó, 2 = oferta no activa/no existe.
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_AplicarOferta @IdUsuario INT,
                                           @IdOferta INT,
                                           @Resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        -- Validar que la oferta exista y esté activa
        IF NOT EXISTS (SELECT 1
                       FROM OfertaTrabajo o
                                JOIN EstadoOferta eo ON o.IdEstadoOferta = eo.IdEstadoOferta
                       WHERE o.IdOferta = @IdOferta
                         AND eo.Nombre = 'ACTIVA')
            BEGIN
                SET @Resultado = 2;
                RETURN;
            END

        -- Validar que no haya aplicado ya
        IF EXISTS (SELECT 1
                   FROM PostulanteOferta
                   WHERE IdUsuario = @IdUsuario
                     AND IdOferta = @IdOferta)
            BEGIN
                SET @Resultado = 1;
                RETURN;
            END

        DECLARE @IdPendiente INT;
        SELECT @IdPendiente = IdEstadoAplicacion
        FROM EstadoAplicacion
        WHERE Nombre = 'PENDIENTE';

        INSERT INTO PostulanteOferta (IdUsuario, IdOferta, IdEstadoAplicacion)
        VALUES (@IdUsuario, @IdOferta, @IdPendiente);

        SET @Resultado = 0;
    END TRY
    BEGIN CATCH
        SET @Resultado = -1;
        THROW;
    END CATCH
END;
GO

-- ------------------------------------------------------------
-- SP: sp_CambiarEstadoAplicacion
-- Actualiza el estado de una postulación (empresa o admin).
-- ------------------------------------------------------------
CREATE OR ALTER PROCEDURE sp_CambiarEstadoAplicacion @IdUsuario INT,
                                                     @IdOferta INT,
                                                     @NuevoEstado VARCHAR(30)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @IdEstado INT;
        SELECT @IdEstado = IdEstadoAplicacion
        FROM EstadoAplicacion
        WHERE Nombre = @NuevoEstado;

        IF @IdEstado IS NULL
            BEGIN
                RAISERROR (N'Estado de aplicación "%s" no existe en el catálogo.', 16, 1, @NuevoEstado);
                RETURN;
            END

        UPDATE PostulanteOferta
        SET IdEstadoAplicacion = @IdEstado
        WHERE IdUsuario = @IdUsuario
          AND IdOferta = @IdOferta;

        IF @@ROWCOUNT = 0
            RAISERROR (N'Aplicación no encontrada para IdUsuario=%d, IdOferta=%d.', 16, 1, @IdUsuario, @IdOferta);
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO