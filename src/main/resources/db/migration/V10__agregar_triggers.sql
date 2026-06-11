-- ------------------------------------------------------------
-- Tabla de auditoría: AuditoriaUsuario
-- Registra INSERT, UPDATE y DELETE sobre la tabla Usuario.
-- ------------------------------------------------------------
CREATE TABLE AuditoriaUsuario
(
    IdAuditoria     INT          NOT NULL IDENTITY (1,1),
    IdUsuario       INT          NOT NULL,
    Accion          VARCHAR(10)  NOT NULL, -- INSERT | UPDATE | DELETE
    CampoModificado VARCHAR(60)  NULL,
    ValorAnterior   VARCHAR(255) NULL,
    ValorNuevo      VARCHAR(255) NULL,
    FechaAccion     DATETIME2    NOT NULL DEFAULT GETDATE(),
    CONSTRAINT PK_AuditoriaUsuario PRIMARY KEY (IdAuditoria),
    CONSTRAINT CK_AuditoriaUsuario_Accion
        CHECK (Accion IN ('INSERT', 'UPDATE', 'DELETE'))
);
GO

-- ------------------------------------------------------------
-- Tabla de auditoría: AuditoriaRol
-- Registra asignaciones y revocaciones de roles (CU-A02).
-- ------------------------------------------------------------
CREATE TABLE AuditoriaRol
(
    IdAuditoria INT         NOT NULL IDENTITY (1,1),
    IdUsuario   INT         NOT NULL,
    IdRol       INT         NOT NULL,
    Accion      VARCHAR(10) NOT NULL, -- ASSIGN | REVOKE
    FechaAccion DATETIME2   NOT NULL DEFAULT GETDATE(),
    CONSTRAINT PK_AuditoriaRol PRIMARY KEY (IdAuditoria),
    CONSTRAINT CK_AuditoriaRol_Accion CHECK (Accion IN ('ASSIGN', 'REVOKE'))
);
GO

-- ------------------------------------------------------------
-- Trigger: trg_AuditarCambiosUsuario
-- Registra en AuditoriaUsuario cualquier cambio en el estado
-- o en los intentos fallidos de la tabla Usuario.
-- Tipo: AFTER INSERT, UPDATE, DELETE
-- ------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_AuditarCambiosUsuario
    ON Usuario
    AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    -- INSERT: nuevo usuario
    IF EXISTS (SELECT 1 FROM inserted) AND NOT EXISTS (SELECT 1 FROM deleted)
        BEGIN
            INSERT INTO AuditoriaUsuario (IdUsuario, Accion, CampoModificado, ValorAnterior, ValorNuevo)
            SELECT i.IdUsuario, 'INSERT', 'Correo', NULL, i.Correo
            FROM inserted i;
        END

    -- DELETE: usuario eliminado
    IF EXISTS (SELECT 1 FROM deleted) AND NOT EXISTS (SELECT 1 FROM inserted)
        BEGIN
            INSERT INTO AuditoriaUsuario (IdUsuario, Accion, CampoModificado, ValorAnterior, ValorNuevo)
            SELECT d.IdUsuario, 'DELETE', 'Correo', d.Correo, NULL
            FROM deleted d;
        END

    -- UPDATE: auditar cambios de estado e intentos fallidos
    IF EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
        BEGIN
            -- Cambio de estado
            INSERT INTO AuditoriaUsuario (IdUsuario, Accion, CampoModificado, ValorAnterior, ValorNuevo)
            SELECT i.IdUsuario,
                   'UPDATE',
                   'IdEstadoUsuario',
                   CAST(d.IdEstadoUsuario AS VARCHAR(10)),
                   CAST(i.IdEstadoUsuario AS VARCHAR(10))
            FROM inserted i
                     JOIN deleted d ON i.IdUsuario = d.IdUsuario
            WHERE i.IdEstadoUsuario <> d.IdEstadoUsuario;

            -- Cambio de intentos fallidos
            INSERT INTO AuditoriaUsuario (IdUsuario, Accion, CampoModificado, ValorAnterior, ValorNuevo)
            SELECT i.IdUsuario,
                   'UPDATE',
                   'IntentosFallidos',
                   CAST(d.IntentosFallidos AS VARCHAR(5)),
                   CAST(i.IntentosFallidos AS VARCHAR(5))
            FROM inserted i
                     JOIN deleted d ON i.IdUsuario = d.IdUsuario
            WHERE i.IntentosFallidos <> d.IntentosFallidos;
        END
END;
GO

-- ------------------------------------------------------------
-- Trigger: trg_VencerOfertas
-- Al insertar o actualizar una oferta, si la FechaVencimiento
-- ya pasó y el estado es ACTIVA, la cambia automáticamente
-- a VENCIDA.
-- ------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_VencerOfertas
    ON OfertaTrabajo
    AFTER INSERT, UPDATE
    AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @IdVencida INT;
    SELECT @IdVencida = IdEstadoOferta
    FROM EstadoOferta
    WHERE Nombre = 'VENCIDA';

    DECLARE @IdActiva INT;
    SELECT @IdActiva = IdEstadoOferta
    FROM EstadoOferta
    WHERE Nombre = 'ACTIVA';

    UPDATE OfertaTrabajo
    SET IdEstadoOferta = @IdVencida
    WHERE IdOferta IN (SELECT IdOferta FROM inserted)
      AND FechaVencimiento < CAST(GETDATE() AS DATE)
      AND IdEstadoOferta = @IdActiva;
END;
GO

-- ------------------------------------------------------------
-- Trigger: trg_AutonumerarExperiencia
-- Asigna automáticamente el NumExp correlativo por postulante
-- al insertar una nueva experiencia laboral.
-- ------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_AutonumerarExperiencia
    ON ExperienciaLaboral
    INSTEAD OF INSERT
    AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO ExperienciaLaboral
    (NumExp, IdUsuario, NombreEmpresa, Puesto, FechaInicio, FechaFin, TrabajoActual, Funciones)
    SELECT
        -- MAX actual por usuario + número de fila dentro del mismo lote por usuario
        ISNULL(
                (SELECT MAX(e.NumExp) FROM ExperienciaLaboral e WHERE e.IdUsuario = i.IdUsuario),
                0
        ) + ROW_NUMBER() OVER (PARTITION BY i.IdUsuario ORDER BY (SELECT NULL)),
        i.IdUsuario,
        i.NombreEmpresa,
        i.Puesto,
        i.FechaInicio,
        i.FechaFin,
        i.TrabajoActual,
        i.Funciones
    FROM inserted i;
END;
GO

-- ------------------------------------------------------------
-- Trigger: trg_AutonumerarFormacion
-- Asigna NumFormacion correlativo por postulante para
-- FormacionAcademica (mismo patrón que ExperienciaLaboral).
-- ------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_AutonumerarFormacion
    ON FormacionAcademica
    INSTEAD OF INSERT
    AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO FormacionAcademica
    (NumFormacion, IdUsuario, Institucion, Titulo, IdNivelEducativo, FechaInicio, FechaFin, EnCurso)
    SELECT ISNULL(
                   (SELECT MAX(f.NumFormacion) FROM FormacionAcademica f WHERE f.IdUsuario = i.IdUsuario),
                   0
           ) + ROW_NUMBER() OVER (PARTITION BY i.IdUsuario ORDER BY (SELECT NULL)),
           i.IdUsuario,
           i.Institucion,
           i.Titulo,
           i.IdNivelEducativo,
           i.FechaInicio,
           i.FechaFin,
           i.EnCurso
    FROM inserted i;
END;
GO

-- ------------------------------------------------------------
-- Trigger: trg_ValidarRolMinimo
-- Impide que un usuario quede sin ningún rol asignado.
-- ------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_ValidarRolMinimo
    ON UsuarioRol
    AFTER DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    -- Verificar si algún usuario afectado quedó sin roles
    IF EXISTS (SELECT d.IdUsuario
               FROM deleted d
               WHERE NOT EXISTS (SELECT 1
                                 FROM UsuarioRol ur
                                 WHERE ur.IdUsuario = d.IdUsuario))
        BEGIN
            RAISERROR (
                'No se puede revocar todos los roles. El usuario debe tener al menos un rol asignado.',
                16, 1
                );
            ROLLBACK TRANSACTION;
            RETURN;
        END
END;
GO

-- ------------------------------------------------------------
-- Trigger: trg_AuditarCambiosRol
-- Registra en AuditoriaRol las asignaciones y revocaciones
-- ------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_AuditarCambiosRol
    ON UsuarioRol
    AFTER INSERT, DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    -- Asignación de roles
    IF EXISTS (SELECT 1 FROM inserted) AND NOT EXISTS (SELECT 1 FROM deleted)
        BEGIN
            INSERT INTO AuditoriaRol (IdUsuario, IdRol, Accion)
            SELECT i.IdUsuario, i.IdRol, 'ASSIGN'
            FROM inserted i;
        END

    -- Revocación de roles
    IF EXISTS (SELECT 1 FROM deleted) AND NOT EXISTS (SELECT 1 FROM inserted)
        BEGIN
            INSERT INTO AuditoriaRol (IdUsuario, IdRol, Accion)
            SELECT d.IdUsuario, d.IdRol, 'REVOKE'
            FROM deleted d;
        END
END;
GO
