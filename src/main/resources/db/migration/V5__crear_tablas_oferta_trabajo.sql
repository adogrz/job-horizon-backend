CREATE TABLE OfertaTrabajo
(
    IdOferta               INT            NOT NULL IDENTITY (1,1),
    Titulo                 VARCHAR(200)   NOT NULL,
    Descripcion            VARCHAR(MAX)   NOT NULL,
    SalarioMin             DECIMAL(10, 2) NULL,
    SalarioMax             DECIMAL(10, 2) NULL,
    NumVacantes            SMALLINT       NOT NULL DEFAULT 1,
    AniosExperienciaMinima SMALLINT       NOT NULL DEFAULT 0, -- años mínimos de experiencia
    FechaPublicacion       DATETIME2      NOT NULL DEFAULT GETDATE(),
    FechaVencimiento       DATE           NOT NULL,
    IdEmpresa              INT            NOT NULL,
    IdTipoContrato         INT            NOT NULL,
    IdNivelEducativo       INT            NOT NULL,
    IdModalidad            INT            NOT NULL,
    IdEstadoOferta         INT            NOT NULL,
    IdDistrito             INT            NOT NULL,
    CONSTRAINT PK_OfertaTrabajo PRIMARY KEY (IdOferta),
    CONSTRAINT FK_OfertaTrabajo_Empresa
        FOREIGN KEY (IdEmpresa) REFERENCES Empresa (IdUsuario)
            ON DELETE CASCADE,                                -- eliminar empresa elimina sus ofertas
    CONSTRAINT FK_OfertaTrabajo_TipoContrato
        FOREIGN KEY (IdTipoContrato) REFERENCES TipoContrato (IdTipoContrato),
    CONSTRAINT FK_OfertaTrabajo_NivelEducativo
        FOREIGN KEY (IdNivelEducativo) REFERENCES NivelEducativo (IdNivelEducativo),
    CONSTRAINT FK_OfertaTrabajo_Modalidad
        FOREIGN KEY (IdModalidad) REFERENCES Modalidad (IdModalidad),
    CONSTRAINT FK_OfertaTrabajo_EstadoOferta
        FOREIGN KEY (IdEstadoOferta) REFERENCES EstadoOferta (IdEstadoOferta),
    CONSTRAINT FK_OfertaTrabajo_Distrito
        FOREIGN KEY (IdDistrito) REFERENCES Distrito (IdDistrito),
    CONSTRAINT CK_OfertaTrabajo_Salario
        CHECK (SalarioMin IS NULL OR SalarioMax IS NULL OR SalarioMax >= SalarioMin),
    CONSTRAINT CK_OfertaTrabajo_Vacantes
        CHECK (NumVacantes >= 1),
    CONSTRAINT CK_OfertaTrabajo_Experiencia
        CHECK (AniosExperienciaMinima >= 0),
    CONSTRAINT CK_OfertaTrabajo_FechaVencimiento
        CHECK (FechaVencimiento > CAST(FechaPublicacion AS DATE))
);
GO

CREATE TABLE OfertaHabilidad
(
    IdOferta         INT NOT NULL,
    IdHabilidad      INT NOT NULL,
    IdNivelHabilidad INT NOT NULL,
    CONSTRAINT PK_OfertaHabilidad PRIMARY KEY (IdOferta, IdHabilidad),
    CONSTRAINT FK_OfertaHabilidad_Oferta
        FOREIGN KEY (IdOferta) REFERENCES OfertaTrabajo (IdOferta)
            ON DELETE CASCADE,
    CONSTRAINT FK_OfertaHabilidad_Habilidad
        FOREIGN KEY (IdHabilidad) REFERENCES Habilidad (IdHabilidad),
    CONSTRAINT FK_OfertaHabilidad_Nivel
        FOREIGN KEY (IdNivelHabilidad) REFERENCES NivelHabilidad (IdNivelHabilidad)
);
GO

CREATE TABLE OfertaIdioma
(
    IdOferta      INT NOT NULL,
    IdIdioma      INT NOT NULL,
    IdNivelIdioma INT NOT NULL, -- nivel mínimo requerido
    CONSTRAINT PK_OfertaIdioma PRIMARY KEY (IdOferta, IdIdioma),
    CONSTRAINT FK_OfertaIdioma_Oferta
        FOREIGN KEY (IdOferta) REFERENCES OfertaTrabajo (IdOferta)
            ON DELETE CASCADE,
    CONSTRAINT FK_OfertaIdioma_Idioma
        FOREIGN KEY (IdIdioma) REFERENCES Idioma (IdIdioma),
    CONSTRAINT FK_OfertaIdioma_Nivel
        FOREIGN KEY (IdNivelIdioma) REFERENCES NivelIdioma (IdNivelIdioma)
);
GO

CREATE TABLE PostulanteOferta
(
    IdUsuario          INT       NOT NULL,
    IdOferta           INT       NOT NULL,
    FechaAplicacion    DATETIME2 NOT NULL DEFAULT GETDATE(),
    IdEstadoAplicacion INT       NOT NULL,
    CONSTRAINT PK_PostulanteOferta PRIMARY KEY (IdUsuario, IdOferta),
    CONSTRAINT FK_PostulanteOferta_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante(IdUsuario)
            ON DELETE NO ACTION,
    CONSTRAINT FK_PostulanteOferta_Oferta
        FOREIGN KEY (IdOferta) REFERENCES OfertaTrabajo(IdOferta)
            ON DELETE NO ACTION,
    CONSTRAINT FK_PostulanteOferta_Estado
        FOREIGN KEY (IdEstadoAplicacion) REFERENCES EstadoAplicacion (IdEstadoAplicacion)
);
GO
