CREATE TABLE Departamento
(
    IdDepartamento INT          NOT NULL IDENTITY (1,1),
    Nombre         VARCHAR(100) NOT NULL,
    CONSTRAINT PK_Departamento PRIMARY KEY (IdDepartamento),
    CONSTRAINT UQ_Departamento_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE Distrito
(
    IdDistrito     INT          NOT NULL IDENTITY (1,1),
    Nombre         VARCHAR(150) NOT NULL,
    IdDepartamento INT          NOT NULL,
    CONSTRAINT PK_Distrito PRIMARY KEY (IdDistrito),
    CONSTRAINT FK_Distrito_Departamento
        FOREIGN KEY (IdDepartamento) REFERENCES Departamento (IdDepartamento)
);
GO

CREATE TABLE EstadoUsuario
(
    IdEstadoUsuario INT         NOT NULL IDENTITY (1,1),
    Nombre          VARCHAR(30) NOT NULL, -- ej. ACTIVO | INACTIVO | BLOQUEADO (parametrizable)
    CONSTRAINT PK_EstadoUsuario PRIMARY KEY (IdEstadoUsuario),
    CONSTRAINT UQ_EstadoUsuario_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE EstadoOferta
(
    IdEstadoOferta INT         NOT NULL IDENTITY (1,1),
    Nombre         VARCHAR(30) NOT NULL, -- ej. ACTIVA | VENCIDA | CERRADA | PAUSADA (parametrizable)
    CONSTRAINT PK_EstadoOferta PRIMARY KEY (IdEstadoOferta),
    CONSTRAINT UQ_EstadoOferta_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE EstadoAplicacion
(
    IdEstadoAplicacion INT         NOT NULL IDENTITY (1,1),
    Nombre             VARCHAR(30) NOT NULL, -- ej. PENDIENTE | REVISADA | CONTACTADO | RECHAZADO (parametrizable)
    CONSTRAINT PK_EstadoAplicacion PRIMARY KEY (IdEstadoAplicacion),
    CONSTRAINT UQ_EstadoAplicacion_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE Genero
(
    IdGenero INT         NOT NULL IDENTITY (1,1),
    Nombre   VARCHAR(30) NOT NULL, -- MASCULINO | FEMENINO | OTRO | PREFIERO_NO_DECIR
    CONSTRAINT PK_Genero PRIMARY KEY (IdGenero),
    CONSTRAINT UQ_Genero_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE TipoDocumento
(
    IdTipoDocumento INT          NOT NULL IDENTITY (1,1),
    Nombre          VARCHAR(50)  NOT NULL, -- DUI | PASAPORTE | CARNET_RESIDENTE
    Descripcion     VARCHAR(200) NULL,
    CONSTRAINT PK_TipoDocumento PRIMARY KEY (IdTipoDocumento),
    CONSTRAINT UQ_TipoDocumento_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE TipoContrato
(
    IdTipoContrato INT         NOT NULL IDENTITY (1,1),
    Nombre         VARCHAR(80) NOT NULL, -- TIEMPO_COMPLETO | MEDIO_TIEMPO | TEMPORAL | FREELANCE
    CONSTRAINT PK_TipoContrato PRIMARY KEY (IdTipoContrato),
    CONSTRAINT UQ_TipoContrato_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE NivelEducativo
(
    IdNivelEducativo INT         NOT NULL IDENTITY (1,1),
    Nombre           VARCHAR(80) NOT NULL, -- BACHILLERATO | TECNICO | LICENCIATURA | MAESTRIA | DOCTORADO
    CONSTRAINT PK_NivelEducativo PRIMARY KEY (IdNivelEducativo),
    CONSTRAINT UQ_NivelEducativo_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE Modalidad
(
    IdModalidad INT         NOT NULL IDENTITY (1,1),
    Nombre      VARCHAR(30) NOT NULL, -- ej. PRESENCIAL | REMOTO | HIBRIDO (parametrizable)
    CONSTRAINT PK_Modalidad PRIMARY KEY (IdModalidad),
    CONSTRAINT UQ_Modalidad_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE CategoriaHabilidad
(
    IdCategoriaHabilidad INT          NOT NULL IDENTITY (1,1),
    Nombre               VARCHAR(100) NOT NULL,
    Descripcion          VARCHAR(300) NULL,
    CONSTRAINT PK_CategoriaHabilidad PRIMARY KEY (IdCategoriaHabilidad),
    CONSTRAINT UQ_CategoriaHabilidad_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE Habilidad
(
    IdHabilidad          INT          NOT NULL IDENTITY (1,1),
    Nombre               VARCHAR(100) NOT NULL,
    Descripcion          VARCHAR(300) NULL,
    IdCategoriaHabilidad INT          NOT NULL,
    CONSTRAINT PK_Habilidad PRIMARY KEY (IdHabilidad),
    CONSTRAINT FK_Habilidad_Categoria
        FOREIGN KEY (IdCategoriaHabilidad) REFERENCES CategoriaHabilidad (IdCategoriaHabilidad)
);
GO

CREATE TABLE Idioma
(
    IdIdioma INT         NOT NULL IDENTITY (1,1),
    Nombre   VARCHAR(80) NOT NULL,
    CONSTRAINT PK_Idioma PRIMARY KEY (IdIdioma),
    CONSTRAINT UQ_Idioma_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE NivelIdioma
(
    IdNivelIdioma    INT         NOT NULL IDENTITY (1,1),
    Nombre           VARCHAR(20) NOT NULL,
    OrdenComparacion TINYINT     NOT NULL, -- 1=A1, 2=A2, 3=B1, 4=B2, 5=C1, 6=C2, 7=NATIVO
    CONSTRAINT PK_NivelIdioma PRIMARY KEY (IdNivelIdioma),
    CONSTRAINT UQ_NivelIdioma_Nombre UNIQUE (Nombre),
    CONSTRAINT UQ_NivelIdioma_Orden UNIQUE (OrdenComparacion)
);
GO

CREATE TABLE NivelHabilidad
(
    IdNivelHabilidad INT         NOT NULL IDENTITY (1,1),
    Nombre           VARCHAR(20) NOT NULL,
    OrdenComparacion TINYINT     NOT NULL, -- 1=BASICO, 2=INTERMEDIO, 3=AVANZADO, 4=EXPERTO
    CONSTRAINT PK_NivelHabilidad PRIMARY KEY (IdNivelHabilidad),
    CONSTRAINT UQ_NivelHabilidad_Nombre UNIQUE (Nombre),
    CONSTRAINT UQ_NivelHabilidad_Orden UNIQUE (OrdenComparacion)
);
GO

CREATE TABLE TipoCertificacion
(
    IdTipoCertificacion INT         NOT NULL IDENTITY (1,1),
    Nombre              VARCHAR(80) NOT NULL,
    CONSTRAINT PK_TipoCertificacion PRIMARY KEY (IdTipoCertificacion),
    CONSTRAINT UQ_TipoCertificacion_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE TipoRecomendacion
(
    IdTipoRecomendacion INT         NOT NULL IDENTITY (1,1),
    Nombre              VARCHAR(30) NOT NULL,
    CONSTRAINT PK_TipoRecomendacion PRIMARY KEY (IdTipoRecomendacion)
);
GO

CREATE TABLE Pais
(
    IdPais INT IDENTITY (1,1) NOT NULL,
    Nombre VARCHAR(100)       NOT NULL,
    CONSTRAINT PK_Pais PRIMARY KEY (IdPais),
    CONSTRAINT UQ_Pais_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE TipoParticipacion
(
    IdTipoParticipacion INT         NOT NULL IDENTITY (1,1),
    Nombre              VARCHAR(30) NOT NULL,
    CONSTRAINT PK_TipoParticipacion PRIMARY KEY (IdTipoParticipacion)
);
GO

CREATE TABLE TipoRedSocial
(
    IdTipoRedSocial INT         NOT NULL IDENTITY (1,1),
    Nombre          VARCHAR(50) NOT NULL, -- LINKEDIN | GITHUB | TWITTER | FACEBOOK | OTRO
    CONSTRAINT PK_TipoRedSocial PRIMARY KEY (IdTipoRedSocial),
    CONSTRAINT UQ_TipoRedSocial_Nombre UNIQUE (Nombre)
);
GO

CREATE TABLE ConfiguracionSistema
(
    Clave       VARCHAR(100) NOT NULL,
    Valor       VARCHAR(500) NOT NULL,
    Descripcion VARCHAR(300) NULL,
    CONSTRAINT PK_ConfiguracionSistema PRIMARY KEY (Clave)
);
GO

INSERT INTO ConfiguracionSistema (Clave, Valor, Descripcion)
VALUES ('MAX_INTENTOS_FALLIDOS', '3', N'Máximo de intentos fallidos antes de bloqueo de cuenta'),
       ('TOKEN_EXPIRACION_HORAS', '24', N'Horas de validez del token de desbloqueo de cuenta'),
       ('COEFICIENTE_HABILIDADES', '0.35', N'Peso (alfa) de habilidades técnicas en el algoritmo de matching'),
       ('COEFICIENTE_ACADEMICO', '0.25', N'Peso (beta) de nivel académico en el algoritmo de matching'),
       ('COEFICIENTE_EXPERIENCIA', '0.20', N'Peso (gamma) de experiencia laboral en el algoritmo de matching'),
       ('COEFICIENTE_IDIOMAS', '0.20', N'Peso (delta) de idiomas en el algoritmo de matching');
GO
