CREATE TABLE Empresa
(
    IdUsuario       INT          NOT NULL,
    NombreComercial VARCHAR(150) NOT NULL,
    RazonSocial     VARCHAR(200) NOT NULL,
    Nit             VARCHAR(17)  NOT NULL, -- formato: 0000-000000-000-0
    SitioWeb        VARCHAR(300) NULL,
    Descripcion     VARCHAR(MAX) NULL,
    LogoUrl         VARCHAR(500) NULL,
    IdDistrito      INT          NOT NULL,
    CONSTRAINT PK_Empresa PRIMARY KEY (IdUsuario),
    CONSTRAINT FK_Empresa_Usuario
        FOREIGN KEY (IdUsuario) REFERENCES Usuario (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_Empresa_Distrito
        FOREIGN KEY (IdDistrito) REFERENCES Distrito (IdDistrito),
    CONSTRAINT UQ_Empresa_Nit UNIQUE (Nit),
    CONSTRAINT CK_Empresa_Nit
        CHECK (Nit LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9]')
);
GO

CREATE TABLE EmpresaTelefono
(
    IdUsuario INT         NOT NULL,
    Telefono  VARCHAR(15) NOT NULL,
    CONSTRAINT PK_EmpresaTelefono PRIMARY KEY (IdUsuario, Telefono),
    CONSTRAINT FK_EmpresaTelefono_Empresa
        FOREIGN KEY (IdUsuario) REFERENCES Empresa (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT CK_EmpresaTelefono_Formato
        CHECK (Telefono LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'
            OR Telefono LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);
GO
