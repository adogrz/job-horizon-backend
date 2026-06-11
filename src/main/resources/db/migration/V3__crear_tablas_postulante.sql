CREATE TABLE Postulante
(
    IdUsuario       INT          NOT NULL,
    Nombres         VARCHAR(100) NOT NULL,
    Apellidos       VARCHAR(100) NOT NULL,
    FechaNacimiento DATE         NOT NULL,
    NumDocumento    VARCHAR(20)  NOT NULL,
    Nup             VARCHAR(20)  NULL,
    Nit             VARCHAR(20)  NULL,
    Direccion       VARCHAR(300) NOT NULL,
    FotoUrl         VARCHAR(500) NULL, -- ruta o URL de la foto
    IdGenero        INT          NOT NULL,
    IdTipoDocumento INT          NOT NULL,
    IdDistrito      INT          NOT NULL,
    CONSTRAINT PK_Postulante PRIMARY KEY (IdUsuario),
    CONSTRAINT FK_Postulante_Usuario
        FOREIGN KEY (IdUsuario) REFERENCES Usuario (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_Postulante_Genero
        FOREIGN KEY (IdGenero) REFERENCES Genero (IdGenero),
    CONSTRAINT FK_Postulante_TipoDocumento
        FOREIGN KEY (IdTipoDocumento) REFERENCES TipoDocumento (IdTipoDocumento),
    CONSTRAINT FK_Postulante_Distrito
        FOREIGN KEY (IdDistrito) REFERENCES Distrito (IdDistrito),
    CONSTRAINT CK_Postulante_NumDocumento
        CHECK (
            -- Si es DUI (IdTipoDocumento = 1): formato 00000000-0
            (IdTipoDocumento = 1 AND NumDocumento LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]-[0-9]')
                -- Si es Pasaporte o Carnet Residente: cualquier valor no vacío de hasta 20 chars
                OR (IdTipoDocumento <> 1 AND LEN(NumDocumento) > 0)
            ),
    CONSTRAINT CK_Postulante_Nup
        CHECK (Nup IS NULL OR (LEN(Nup) = 9 AND Nup NOT LIKE '%[^0-9]%')),
    CONSTRAINT CK_Postulante_Nit
        CHECK (Nit IS NULL OR (
            -- Formato tradicional de NIT: 0000-000000-000-0
            Nit LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9]'
                -- Formato unificado moderno con DUI: 00000000-0
                OR Nit LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]-[0-9]'
            ))
);
GO

CREATE TABLE PostulanteTelefono
(
    IdUsuario INT         NOT NULL,
    Telefono  VARCHAR(15) NOT NULL,
    CONSTRAINT PK_PostulanteTelefono PRIMARY KEY (IdUsuario, Telefono),
    CONSTRAINT FK_PostulanteTelefono_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT CK_PostulanteTelefono_Formato
        CHECK (Telefono LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'
            OR Telefono LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);
GO

CREATE TABLE PostulanteRedSocial
(
    IdUsuario       INT          NOT NULL,
    IdTipoRedSocial INT          NOT NULL,
    Url             VARCHAR(500) NOT NULL,
    CONSTRAINT PK_PostulanteRedSocial PRIMARY KEY (IdUsuario, IdTipoRedSocial),
    CONSTRAINT FK_PostulanteRedSocial_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_PostulanteRedSocial_Tipo
        FOREIGN KEY (IdTipoRedSocial) REFERENCES TipoRedSocial (IdTipoRedSocial)
);
GO

CREATE TABLE ExperienciaLaboral
(
    NumExp           INT          NOT NULL,
    IdUsuario        INT          NOT NULL,
    NombreEmpresa    VARCHAR(150) NOT NULL,
    Puesto           VARCHAR(120) NOT NULL,
    FechaInicio      DATE         NOT NULL,
    FechaFin         DATE         NULL, -- NULL si Trabajo_Actual = 1
    TrabajoActual    BIT          NOT NULL DEFAULT 0,
    Funciones        VARCHAR(MAX) NOT NULL,
    TelefonoContacto VARCHAR(15)  NULL,
    CorreoContacto   VARCHAR(150) NULL,
    CONSTRAINT PK_ExperienciaLaboral PRIMARY KEY (NumExp, IdUsuario),
    CONSTRAINT FK_ExperienciaLaboral_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT CK_ExperienciaLaboral_Fechas
        CHECK (FechaFin IS NULL OR FechaFin >= FechaInicio),
    CONSTRAINT CK_ExperienciaLaboral_Actual
        CHECK (TrabajoActual = 0 OR (TrabajoActual = 1 AND FechaFin IS NULL)),
    CONSTRAINT CK_ExperienciaLaboral_Telefono
        CHECK (TelefonoContacto IS NULL
            OR TelefonoContacto LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'
            OR TelefonoContacto LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_ExperienciaLaboral_Correo
        CHECK (CorreoContacto IS NULL
            OR (CorreoContacto LIKE '%@%.%'
                AND LEN(CorreoContacto) >= 6))
);
GO

CREATE TABLE FormacionAcademica
(
    NumFormacion     INT          NOT NULL,
    IdUsuario        INT          NOT NULL,
    Institucion      VARCHAR(200) NOT NULL,
    Titulo           VARCHAR(200) NOT NULL,
    IdNivelEducativo INT          NOT NULL,
    FechaInicio      DATE         NOT NULL,
    FechaFin         DATE         NULL, -- NULL si EnCurso = 1
    EnCurso          BIT          NOT NULL DEFAULT 0,
    CONSTRAINT PK_FormacionAcademica PRIMARY KEY (NumFormacion, IdUsuario),
    CONSTRAINT FK_FormacionAcademica_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_FormacionAcademica_NivelEducativo
        FOREIGN KEY (IdNivelEducativo) REFERENCES NivelEducativo (IdNivelEducativo),
    CONSTRAINT CK_FormacionAcademica_Fechas
        CHECK (FechaFin IS NULL OR FechaFin >= FechaInicio),
    CONSTRAINT CK_FormacionAcademica_EnCurso
        CHECK (EnCurso = 0 OR (EnCurso = 1 AND FechaFin IS NULL))
);
GO

CREATE TABLE Certificacion
(
    CodCert             INT          NOT NULL,
    IdUsuario           INT          NOT NULL,
    CodigoCertificacion VARCHAR(100) NULL,
    Nombre              VARCHAR(200) NOT NULL,
    IdTipoCertificacion INT          NOT NULL,
    Institucion         VARCHAR(200) NOT NULL,
    FechaObtencion      DATE         NOT NULL,
    ArchivoUrl          VARCHAR(500) NULL,
    CONSTRAINT PK_Certificacion PRIMARY KEY (CodCert, IdUsuario),
    CONSTRAINT FK_Certificacion_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_Certificacion_Tipo
        FOREIGN KEY (IdTipoCertificacion) REFERENCES TipoCertificacion (IdTipoCertificacion)
);

CREATE TABLE Logro
(
    NumLogro    INT          NOT NULL,
    IdUsuario   INT          NOT NULL,
    Descripcion VARCHAR(MAX) NOT NULL,
    Fecha       DATE         NOT NULL,
    CONSTRAINT PK_Logro PRIMARY KEY (NumLogro, IdUsuario),
    CONSTRAINT FK_Logro_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE
);
GO

CREATE TABLE Recomendacion
(
    NumRecomendacion    INT          NOT NULL,
    IdUsuario           INT          NOT NULL,
    NombreContacto      VARCHAR(150) NOT NULL,
    TelefonoContacto    VARCHAR(15)  NOT NULL,
    IdTipoRecomendacion INT          NOT NULL,
    CONSTRAINT PK_Recomendacion PRIMARY KEY (NumRecomendacion, IdUsuario),
    CONSTRAINT FK_Recomendacion_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_Recomendacion_Tipo
        FOREIGN KEY (IdTipoRecomendacion) REFERENCES TipoRecomendacion (IdTipoRecomendacion)
);
GO

CREATE TABLE Evento
(
    NumEvento           INT          NOT NULL,
    IdUsuario           INT          NOT NULL,
    NombreEvento        VARCHAR(200) NOT NULL,
    Lugar               VARCHAR(200) NOT NULL,
    Anfitrion           VARCHAR(200) NOT NULL,
    Fecha               DATE         NOT NULL,
    IdTipoParticipacion INT          NOT NULL,
    IdPais              INT          NOT NULL,
    CONSTRAINT PK_Evento PRIMARY KEY (NumEvento, IdUsuario),
    CONSTRAINT FK_Evento_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_Evento_TipoParticipacion
        FOREIGN KEY (IdTipoParticipacion) REFERENCES TipoParticipacion (IdTipoParticipacion),
    CONSTRAINT FK_Evento_Pais
        FOREIGN KEY (IdPais) REFERENCES Pais (IdPais)
);
GO

CREATE TABLE Publicacion
(
    NumPublicacion   INT          NOT NULL,
    IdUsuario        INT          NOT NULL,
    Titulo           VARCHAR(300) NOT NULL,
    LugarPublicacion VARCHAR(200) NOT NULL,
    Fecha            DATE         NOT NULL,
    Isbn             VARCHAR(20)  NULL, -- puede ser NULL (artículos sin ISBN)
    Edicion          VARCHAR(100) NULL,
    CONSTRAINT PK_Publicacion PRIMARY KEY (NumPublicacion, IdUsuario),
    CONSTRAINT FK_Publicacion_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE
);
GO

CREATE TABLE Examen
(
    NumExamen  INT          NOT NULL,
    IdUsuario  INT          NOT NULL,
    Tipo       VARCHAR(100) NOT NULL,
    Resultado  VARCHAR(100) NOT NULL,
    Fecha      DATE         NOT NULL,
    ArchivoUrl VARCHAR(500) NULL,
    CONSTRAINT PK_Examen PRIMARY KEY (NumExamen, IdUsuario),
    CONSTRAINT FK_Examen_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE
);
GO

CREATE TABLE PostulanteHabilidad
(
    IdUsuario        INT NOT NULL,
    IdHabilidad      INT NOT NULL,
    IdNivelHabilidad INT NOT NULL,
    CONSTRAINT PK_PostulanteHabilidad PRIMARY KEY (IdUsuario, IdHabilidad),
    CONSTRAINT FK_PostulanteHabilidad_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_PostulanteHabilidad_Habilidad
        FOREIGN KEY (IdHabilidad) REFERENCES Habilidad (IdHabilidad),
    CONSTRAINT FK_PostulanteHabilidad_Nivel
        FOREIGN KEY (IdNivelHabilidad) REFERENCES NivelHabilidad (IdNivelHabilidad)
);
GO

CREATE TABLE PostulanteIdioma
(
    IdUsuario           INT NOT NULL,
    IdIdioma            INT NOT NULL,
    IdNivelLectura      INT NOT NULL,
    IdNivelEscritura    INT NOT NULL,
    IdNivelConversacion INT NOT NULL,
    IdNivelEscucha      INT NOT NULL,
    CONSTRAINT PK_PostulanteIdioma PRIMARY KEY (IdUsuario, IdIdioma),
    CONSTRAINT FK_PostulanteIdioma_Postulante
        FOREIGN KEY (IdUsuario) REFERENCES Postulante (IdUsuario)
            ON DELETE CASCADE,
    CONSTRAINT FK_PostulanteIdioma_Idioma
        FOREIGN KEY (IdIdioma) REFERENCES Idioma (IdIdioma),
    CONSTRAINT FK_PostulanteIdioma_NivelLectura
        FOREIGN KEY (IdNivelLectura) REFERENCES NivelIdioma (IdNivelIdioma),
    CONSTRAINT FK_PostulanteIdioma_NivelEscritura
        FOREIGN KEY (IdNivelEscritura) REFERENCES NivelIdioma (IdNivelIdioma),
    CONSTRAINT FK_PostulanteIdioma_NivelConversacion
        FOREIGN KEY (IdNivelConversacion) REFERENCES NivelIdioma (IdNivelIdioma),
    CONSTRAINT FK_PostulanteIdioma_NivelEscucha
        FOREIGN KEY (IdNivelEscucha) REFERENCES NivelIdioma (IdNivelIdioma)
);
GO