CREATE TABLE Usuario
(
    IdUsuario        INT          NOT NULL IDENTITY (1,1),
    Correo           VARCHAR(150) NOT NULL,
    PasswordHash     VARCHAR(255) NOT NULL, -- BCrypt (60-72 chars), VARCHAR(255) por seguridad
    IntentosFallidos TINYINT      NOT NULL DEFAULT 0,
    FechaRegistro    DATETIME2    NOT NULL DEFAULT GETDATE(),
    TokenDesbloqueo  VARCHAR(100) NULL,     -- token temporal para correo de desbloqueo
    FechaTokenExp    DATETIME2    NULL,     -- expiración del token
    IdEstadoUsuario  INT          NOT NULL,
    CONSTRAINT PK_Usuario PRIMARY KEY (IdUsuario),
    CONSTRAINT UQ_Usuario_Correo UNIQUE (Correo),
    CONSTRAINT FK_Usuario_Estado
        FOREIGN KEY (IdEstadoUsuario) REFERENCES EstadoUsuario (IdEstadoUsuario),
    CONSTRAINT CK_Usuario_Intentos CHECK (IntentosFallidos >= 0 AND IntentosFallidos <= 10)
);
GO

CREATE TABLE Administrador
(
    IdUsuario INT NOT NULL,
    CONSTRAINT PK_Administrador PRIMARY KEY (IdUsuario),
    CONSTRAINT FK_Administrador_Usuario
        FOREIGN KEY (IdUsuario) REFERENCES Usuario (IdUsuario)
            ON DELETE CASCADE
);
GO

CREATE TABLE Rol
(
    IdRol       INT          NOT NULL IDENTITY (1,1),
    Nombre      VARCHAR(80)  NOT NULL,
    Descripcion VARCHAR(300) NULL,
    CONSTRAINT PK_Rol PRIMARY KEY (IdRol),
    CONSTRAINT UQ_Rol_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE Privilegio
(
    IdPrivilegio INT          NOT NULL IDENTITY (1,1),
    Nombre       VARCHAR(80)  NOT NULL, -- clave usada en @PreAuthorize y JWT
    NombreMenu   VARCHAR(100) NOT NULL, -- texto visible en el menú
    Ruta         VARCHAR(200) NULL,     -- ruta Angular (opcional, para menú dinámico)
    CONSTRAINT PK_Privilegio PRIMARY KEY (IdPrivilegio),
    CONSTRAINT UQ_Privilegio_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE UsuarioRol
(
    IdUsuario INT NOT NULL,
    IdRol     INT NOT NULL,
    CONSTRAINT PK_UsuarioRol PRIMARY KEY (IdUsuario, IdRol),
    CONSTRAINT FK_UsuarioRol_Usuario
        FOREIGN KEY (IdUsuario) REFERENCES Usuario (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_UsuarioRol_Rol
        FOREIGN KEY (IdRol) REFERENCES Rol (IdRol)
);
GO

CREATE TABLE RolPrivilegio
(
    IdRol        INT NOT NULL,
    IdPrivilegio INT NOT NULL,
    CONSTRAINT PK_RolPrivilegio PRIMARY KEY (IdRol, IdPrivilegio),
    CONSTRAINT FK_RolPrivilegio_Rol
        FOREIGN KEY (IdRol) REFERENCES Rol (IdRol)
            ON DELETE CASCADE,
    CONSTRAINT FK_RolPrivilegio_Privilegio
        FOREIGN KEY (IdPrivilegio) REFERENCES Privilegio (IdPrivilegio)
);
GO
