-- Migration: V11__agregar_pesos_oferta.sql
ALTER TABLE OfertaTrabajo
ADD PesoHabilidades DECIMAL(5, 2) NOT NULL DEFAULT 0.35,
    PesoAcademico   DECIMAL(5, 2) NOT NULL DEFAULT 0.25,
    PesoExperiencia DECIMAL(5, 2) NOT NULL DEFAULT 0.20,
    PesoIdiomas     DECIMAL(5, 2) NOT NULL DEFAULT 0.20;
GO
