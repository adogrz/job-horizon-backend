----------------------------------------------------------
-- Función: fn_CalcularEdad
-- Calcula la edad en años a partir de una fecha de nacimiento.
-- Cubre el atributo derivado "Edad" del EER sin persistirlo.
-- ------------------------------------------------------------
CREATE OR ALTER FUNCTION fn_CalcularEdad(
    @FechaNacimiento DATE
)
    RETURNS INT
AS
BEGIN
    RETURN DATEDIFF(YEAR, @FechaNacimiento, GETDATE())
        - IIF(MONTH(@FechaNacimiento) > MONTH(GETDATE())
                  OR (MONTH(@FechaNacimiento) = MONTH(GETDATE())
                AND DAY(@FechaNacimiento) > DAY(GETDATE())), 1, 0);
END;
GO

-- ------------------------------------------------------------
-- Función: fn_PorcentajeCompletitudPerfil
-- Calcula qué tan completo está el perfil de un postulante
-- en base a las 4 secciones requeridas.
-- Útil para mostrar un indicador de progreso en el frontend.
-- ------------------------------------------------------------
CREATE OR ALTER FUNCTION fn_PorcentajeCompletitudPerfil(
    @IdUsuario INT
)
    RETURNS DECIMAL(5, 2)
AS
BEGIN
    DECLARE @Puntaje INT = 0;
    DECLARE @Total INT = 4;
    -- 4 secciones del perfil

    -- Sección 1: datos personales (ya existe si hay registro en Postulante)
    IF EXISTS (SELECT 1 FROM Postulante WHERE IdUsuario = @IdUsuario)
        SET @Puntaje = @Puntaje + 1;

    -- Sección 2: al menos 1 experiencia laboral
    IF EXISTS (SELECT 1 FROM ExperienciaLaboral WHERE IdUsuario = @IdUsuario)
        SET @Puntaje = @Puntaje + 1;

    -- Sección 3: al menos 1 formación académica
    IF EXISTS (SELECT 1 FROM FormacionAcademica WHERE IdUsuario = @IdUsuario)
        SET @Puntaje = @Puntaje + 1;

    -- Sección 4: al menos 1 habilidad técnica
    IF EXISTS (SELECT 1 FROM PostulanteHabilidad WHERE IdUsuario = @IdUsuario)
        SET @Puntaje = @Puntaje + 1;

    RETURN CAST(@Puntaje AS DECIMAL(5, 2)) / @Total * 100;
END;
GO

-- ------------------------------------------------------------
-- Función: fn_PuntajeMatching
-- Implementa el algoritmo de matching completo de 4 variables:
--
--   Puntaje = (α × H) + (β × A) + (γ × E) + (δ × I)  × 100
--
--   H = Habilidades técnicas  (porcentaje de skills que cumplen nivel mínimo)
--   A = Nivel Académico       (1.00 si cumple, 0.00 si no)
--   E = Experiencia Laboral   (años acumulados vs años requeridos)
--   I = Idiomas               (idiomas donde TODAS las 4 destrezas cumplen nivel)
--
-- Los coeficientes α, β, γ, δ son parametrizables en ConfiguracionSistema.
-- Los niveles se comparan con OrdenComparacion (entero), no con el ID IDENTITY,
-- lo que hace la comparación robusta ante reordenamientos del catálogo.
--
-- Retorna un valor entre 0.00 y 100.00.
-- ------------------------------------------------------------
CREATE OR ALTER FUNCTION fn_PuntajeMatching(
    @IdUsuario INT,
    @IdOferta INT
)
    RETURNS DECIMAL(5, 2)
AS
BEGIN
    -- ============================================================
    -- Leer coeficientes ponderados desde ConfiguracionSistema
    -- ============================================================
    DECLARE @Alpha DECIMAL(5, 2); -- peso de Habilidades
    DECLARE @Beta DECIMAL(5, 2); -- peso de Nivel Académico
    DECLARE @Gamma DECIMAL(5, 2); -- peso de Experiencia Laboral
    DECLARE @Delta DECIMAL(5, 2); -- peso de Idiomas

    SELECT @Alpha = CAST(Valor AS DECIMAL(5, 2))
    FROM ConfiguracionSistema
    WHERE Clave = 'COEFICIENTE_HABILIDADES';
    SELECT @Beta = CAST(Valor AS DECIMAL(5, 2))
    FROM ConfiguracionSistema
    WHERE Clave = 'COEFICIENTE_ACADEMICO';
    SELECT @Gamma = CAST(Valor AS DECIMAL(5, 2))
    FROM ConfiguracionSistema
    WHERE Clave = 'COEFICIENTE_EXPERIENCIA';
    SELECT @Delta = CAST(Valor AS DECIMAL(5, 2))
    FROM ConfiguracionSistema
    WHERE Clave = 'COEFICIENTE_IDIOMAS';

    -- Valores de respaldo si no existe la configuración
    IF @Alpha IS NULL SET @Alpha = 0.35;
    IF @Beta IS NULL SET @Beta = 0.25;
    IF @Gamma IS NULL SET @Gamma = 0.20;
    IF @Delta IS NULL SET @Delta = 0.20;

    -- ============================================================
    -- H: Habilidades Técnicas  (0.00 – 1.00)
    -- Cuenta las habilidades donde el nivel del postulante (OrdenComparacion)
    -- es mayor o igual al nivel mínimo requerido por la oferta.
    -- ============================================================
    DECLARE @HabilidadesReq INT;
    DECLARE @HabilidadesOk INT;

    SELECT @HabilidadesReq = COUNT(*)
    FROM OfertaHabilidad
    WHERE IdOferta = @IdOferta;

    SELECT @HabilidadesOk = COUNT(*)
    FROM PostulanteHabilidad ph
             JOIN OfertaHabilidad oh ON ph.IdHabilidad = oh.IdHabilidad
        AND oh.IdOferta = @IdOferta
             JOIN NivelHabilidad nph ON ph.IdNivelHabilidad = nph.IdNivelHabilidad
             JOIN NivelHabilidad noh ON oh.IdNivelHabilidad = noh.IdNivelHabilidad
    WHERE ph.IdUsuario = @IdUsuario
      AND nph.OrdenComparacion >= noh.OrdenComparacion;

    DECLARE @H DECIMAL(5, 2) =
        CASE
            WHEN @HabilidadesReq = 0 THEN 1.00
            ELSE CAST(@HabilidadesOk AS DECIMAL(5, 2)) / CAST(@HabilidadesReq AS DECIMAL(5, 2))
            END;

    -- ============================================================
    -- A: Nivel Académico  (0.00 o 1.00)
    -- Compara el nivel educativo máximo del postulante con el
    -- nivel mínimo requerido por la oferta, usando OrdenComparacion
    -- de NivelEducativo para evitar dependencia del ID IDENTITY.
    -- ============================================================
    DECLARE @NivelReqOferta INT;
    SELECT @NivelReqOferta = IdNivelEducativo
    FROM OfertaTrabajo
    WHERE IdOferta = @IdOferta;

    -- Orden del nivel requerido por la oferta
    DECLARE @OrdReq INT = 0;
    SELECT @OrdReq = ne.OrdenComparacion
    FROM NivelEducativo ne
    WHERE ne.IdNivelEducativo = @NivelReqOferta;

    -- Orden máximo alcanzado por el postulante
    DECLARE @OrdMax INT = 0;
    SELECT @OrdMax = ISNULL(MAX(ne2.OrdenComparacion), 0)
    FROM FormacionAcademica fa
             JOIN NivelEducativo ne2 ON fa.IdNivelEducativo = ne2.IdNivelEducativo
    WHERE fa.IdUsuario = @IdUsuario;

    DECLARE @A DECIMAL(5, 2) =
        CASE WHEN @OrdMax >= @OrdReq THEN 1.00 ELSE 0.00 END;

    -- ============================================================
    -- E: Experiencia Laboral  (0.00 – 1.00)
    -- Suma los años acumulados de experiencia del postulante y
    -- compara contra los años mínimos requeridos por la oferta.
    -- ============================================================
    DECLARE @AniosReq DECIMAL(5, 2) = 0;
    SELECT @AniosReq = ISNULL(AniosExperienciaMinima, 0)
    FROM OfertaTrabajo
    WHERE IdOferta = @IdOferta;

    DECLARE @AniosPostulante DECIMAL(5, 2) = 0;
    SELECT @AniosPostulante = ISNULL(
            SUM(DATEDIFF(MONTH, FechaInicio, ISNULL(FechaFin, GETDATE())) / 12.0),
            0)
    FROM ExperienciaLaboral
    WHERE IdUsuario = @IdUsuario;

    DECLARE @E DECIMAL(5, 2) =
        CASE
            WHEN @AniosReq = 0 THEN 1.00 -- sin requisito → 100%
            WHEN @AniosPostulante >= @AniosReq THEN 1.00
            ELSE @AniosPostulante / @AniosReq
            END;
    IF @E > 1.00 SET @E = 1.00;

    -- ============================================================
    -- I: Idiomas  (0.00 – 1.00)
    -- Un idioma "coincide" si el postulante alcanza el nivel mínimo
    -- requerido en TODAS las 4 destrezas: lectura, escritura,
    -- conversación y escucha. Se usa OrdenComparacion de NivelIdioma.
    -- ============================================================
    DECLARE @IdiomasReq INT;
    DECLARE @IdiomasOk INT;

    SELECT @IdiomasReq = COUNT(*)
    FROM OfertaIdioma
    WHERE IdOferta = @IdOferta;

    SELECT @IdiomasOk = COUNT(*)
    FROM PostulanteIdioma pi2
             JOIN OfertaIdioma oi ON pi2.IdIdioma = oi.IdIdioma
        AND oi.IdOferta = @IdOferta
             JOIN NivelIdioma nr ON oi.IdNivelIdioma = nr.IdNivelIdioma
             JOIN NivelIdioma nLec ON pi2.IdNivelLectura = nLec.IdNivelIdioma
             JOIN NivelIdioma nEsc ON pi2.IdNivelEscritura = nEsc.IdNivelIdioma
             JOIN NivelIdioma nCon ON pi2.IdNivelConversacion = nCon.IdNivelIdioma
             JOIN NivelIdioma nLis ON pi2.IdNivelEscucha = nLis.IdNivelIdioma
    WHERE pi2.IdUsuario = @IdUsuario
      -- Nivel mínimo de las 4 destrezas debe cubrir el nivel requerido
      AND (SELECT MIN(v)
           FROM (VALUES (nLec.OrdenComparacion),
                        (nEsc.OrdenComparacion),
                        (nCon.OrdenComparacion),
                        (nLis.OrdenComparacion)) AS T(v)) >= nr.OrdenComparacion;

    DECLARE @I DECIMAL(5, 2) =
        CASE
            WHEN @IdiomasReq = 0 THEN 1.00
            ELSE CAST(@IdiomasOk AS DECIMAL(5, 2)) / CAST(@IdiomasReq AS DECIMAL(5, 2))
            END;

    -- ============================================================
    -- PUNTAJE FINAL  (escala 0.00 – 100.00)
    --   Puntaje = (α×H + β×A + γ×E + δ×I) × 100
    -- ============================================================
    RETURN CAST((@Alpha * @H + @Beta * @A + @Gamma * @E + @Delta * @I) * 100 AS DECIMAL(5, 2));
END;
GO