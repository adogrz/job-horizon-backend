-- Usuarios
CREATE INDEX IX_Usuario_EstadoUsuario     ON Usuario(IdEstadoUsuario);

-- Postulante
CREATE INDEX IX_Postulante_Distrito       ON Postulante(IdDistrito);
CREATE INDEX IX_Postulante_NombreCompleto ON Postulante(Apellidos, Nombres);

-- Empresa
CREATE INDEX IX_Empresa_Distrito          ON Empresa(IdDistrito);
CREATE INDEX IX_Empresa_NombreComercial   ON Empresa(NombreComercial);

-- OfertaTrabajo
CREATE INDEX IX_OfertaTrabajo_Empresa     ON OfertaTrabajo(IdEmpresa);
CREATE INDEX IX_OfertaTrabajo_Experiencia ON OfertaTrabajo(AniosExperienciaMinima);

CREATE INDEX IX_OfertaTrabajo_FiltrosDinamicos
    ON OfertaTrabajo(IdEstadoOferta, IdDistrito, IdModalidad, IdTipoContrato, IdNivelEducativo);

CREATE INDEX IX_OfertaTrabajo_Vencimiento
    ON OfertaTrabajo(FechaVencimiento)
    INCLUDE (Titulo, IdEmpresa);

-- PostulanteOferta
CREATE INDEX IX_PostulanteOferta_Oferta   ON PostulanteOferta(IdOferta);
CREATE INDEX IX_PostulanteOferta_Estado   ON PostulanteOferta(IdEstadoAplicacion);

-- Matching
CREATE INDEX IX_PostulanteHabilidad_Habilidad ON PostulanteHabilidad(IdHabilidad);
CREATE INDEX IX_PostulanteIdioma_Idioma        ON PostulanteIdioma(IdIdioma);

-- Distrito
CREATE INDEX IX_Distrito_Departamento     ON Distrito(IdDepartamento);