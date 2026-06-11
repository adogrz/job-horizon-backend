-- ============================================================
--  Datos base requeridos para el funcionamiento del sistema.
--  Estos valores son parametrizables desde el panel de admin.
-- ============================================================

-- ------------------------------------------------------------
-- Estados de usuario
-- El sistema bloquea cuentas tras intentos fallidos (BLOQUEADO),
-- permite dar de baja (INACTIVO) y reactivar (ACTIVO).
-- ------------------------------------------------------------
INSERT INTO EstadoUsuario (Nombre)
VALUES ('ACTIVO'),
       ('INACTIVO'),
       ('BLOQUEADO');

-- ------------------------------------------------------------
-- Estados de oferta de trabajo
-- ------------------------------------------------------------
INSERT INTO EstadoOferta (Nombre)
VALUES ('ACTIVA'),
       ('VENCIDA'),
       ('CERRADA'),
       ('PAUSADA');

-- ------------------------------------------------------------
-- Estados de aplicación (postulante → oferta)
-- ------------------------------------------------------------
INSERT INTO EstadoAplicacion (Nombre)
VALUES ('PENDIENTE'),
       ('REVISADA'),
       ('CONTACTADO'),
       ('RECHAZADO');

-- ------------------------------------------------------------
-- Géneros
-- ------------------------------------------------------------
INSERT INTO Genero (Nombre)
VALUES ('MASCULINO'),
       ('FEMENINO'),
       ('OTRO'),
       ('PREFIERO_NO_DECIR');

-- ------------------------------------------------------------
-- Tipos de documento de identidad
-- Orientado al mercado salvadoreño
-- ------------------------------------------------------------
INSERT INTO TipoDocumento (Nombre, Descripcion)
VALUES ('DUI', N'Documento Único de Identidad'),
       ('PASAPORTE', 'Pasaporte vigente'),
       ('CARNET_RESIDENTE', 'Carnet de residente extranjero');

-- ------------------------------------------------------------
-- Tipos de contrato
-- ------------------------------------------------------------
INSERT INTO TipoContrato (Nombre)
VALUES ('TIEMPO_COMPLETO'),
       ('MEDIO_TIEMPO'),
       ('TEMPORAL'),
       ('FREELANCE');

-- ------------------------------------------------------------
-- Niveles educativos
-- Referenciados por OfertaTrabajo y FormacionAcademica.
-- OrdenComparacion permite comparaciones robustas en el matching.
-- ------------------------------------------------------------
INSERT INTO NivelEducativo (Nombre, OrdenComparacion)
VALUES ('BACHILLERATO', 1),
       ('TECNICO', 2),
       ('INGENIERIA', 3),
       ('LICENCIATURA', 4),
       ('MAESTRIA', 5),
       ('DOCTORADO', 6);

-- ------------------------------------------------------------
-- Modalidades de trabajo
-- ------------------------------------------------------------
INSERT INTO Modalidad (Nombre)
VALUES ('PRESENCIAL'),
       ('REMOTO'),
       ('HIBRIDO');

-- ------------------------------------------------------------
-- Niveles de habilidad
-- OrdenComparacion permite comparaciones seguras en el matching
-- ------------------------------------------------------------
INSERT INTO NivelHabilidad (Nombre, OrdenComparacion)
VALUES ('BASICO', 1),
       ('INTERMEDIO', 2),
       ('AVANZADO', 3),
       ('EXPERTO', 4);

-- ------------------------------------------------------------
-- Niveles de idioma (escala MCER)
-- ------------------------------------------------------------
INSERT INTO NivelIdioma (Nombre, OrdenComparacion)
VALUES ('A1', 1),
       ('A2', 2),
       ('B1', 3),
       ('B2', 4),
       ('C1', 5),
       ('C2', 6),
       ('NATIVO', 7);

-- ------------------------------------------------------------
-- Tipos de certificación
-- ------------------------------------------------------------
INSERT INTO TipoCertificacion (Nombre)
VALUES ('TECNOLOGIA'),
       ('IDIOMA'),
       ('GESTION_PROYECTOS'),
       ('SEGURIDAD'),
       ('OTRO');

-- ------------------------------------------------------------
-- Tipos de recomendación
-- ------------------------------------------------------------
INSERT INTO TipoRecomendacion (Nombre)
VALUES ('PROFESIONAL'),
       ('PERSONAL'),
       ('ACADEMICA');

-- Países para participación en eventos
-- ------------------------------------------------------------
INSERT INTO Pais (Nombre)
VALUES ('El Salvador'),
       ('Guatemala'),
       ('Honduras'),
       ('Nicaragua'),
       ('Costa Rica'),
       (N'Panamá'),
       (N'México'),
       ('Estados Unidos');
GO

-- ------------------------------------------------------------
-- Tipos de participación en eventos
-- ------------------------------------------------------------
INSERT INTO TipoParticipacion (Nombre)
VALUES ('PONENTE'),
       ('ASISTENTE'),
       ('ORGANIZADOR'),
       ('PATROCINADOR');

-- ------------------------------------------------------------
-- Tipos de red social
-- ------------------------------------------------------------
INSERT INTO TipoRedSocial (Nombre)
VALUES ('LINKEDIN'),
       ('GITHUB'),
       ('TWITTER'),
       ('FACEBOOK'),
       ('OTRO');

-- Departamentos de El Salvador (14)
-- Inserción en orden: el IdDepartamento resultante es 1-14
-- conforme al orden aquí definido.
INSERT INTO Departamento (Nombre)
VALUES (N'Ahuachapán'),   -- 1
       (N'Santa Ana'),    -- 2
       (N'Sonsonate'),    -- 3
       (N'Chalatenango'), -- 4
       (N'La Libertad'),  -- 5
       (N'San Salvador'), -- 6
       (N'Cuscatlán'),    -- 7
       (N'La Paz'),       -- 8
       (N'Cabañas'),      -- 9
       (N'San Vicente'),  -- 10
       (N'Usulután'),     -- 11
       (N'San Miguel'),   -- 12
       (N'Morazán'),      -- 13
       (N'La Unión');     -- 14

-- ============================================================
-- 262 Distritos de El Salvador
-- Los anteriores 262 municipios pasan a llamarse distritos.
-- ============================================================

-- Ahuachapán (IdDepartamento = 1) — 12 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Atiquizaya', 1),
       (N'El Refugio', 1),
       (N'San Lorenzo', 1),
       (N'Turín', 1),
       (N'Ahuachapán', 1),
       (N'Apaneca', 1),
       (N'Concepción de Ataco', 1),
       (N'Tacuba', 1),
       (N'Guaymango', 1),
       (N'Jujutla', 1),
       (N'San Francisco Menéndez', 1),
       (N'San Pedro Puxtla', 1);

-- Santa Ana (IdDepartamento = 2) — 13 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Masahuat', 2),
       (N'Metapán', 2),
       (N'Santa Rosa Guachipilín', 2),
       (N'Texistepeque', 2),
       (N'Santa Ana', 2),
       (N'Coatepeque', 2),
       (N'El Congo', 2),
       (N'Candelaria de la Frontera', 2),
       (N'Chalchuapa', 2),
       (N'El Porvenir', 2),
       (N'San Antonio Pajonal', 2),
       (N'San Sebastián Salitrillo', 2),
       (N'Santiago de la Frontera', 2);

-- Sonsonate (IdDepartamento = 3) — 16 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Juayúa', 3),
       (N'Nahuizalco', 3),
       (N'Salcoatitán', 3),
       (N'Santa Catarina Masahuat', 3),
       (N'Sonsonate', 3),
       (N'Sonzacate', 3),
       (N'Nahulingo', 3),
       (N'San Antonio del Monte', 3),
       (N'Santo Domingo de Guzmán', 3),
       (N'Izalco', 3),
       (N'Armenia', 3),
       (N'Caluco', 3),
       (N'San Julián', 3),
       (N'Cuisnahuat', 3),
       (N'Santa Isabel Ishuatán', 3),
       (N'Acajutla', 3);

-- Chalatenango (IdDepartamento = 4) — 33 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'La Palma', 4),
       (N'Citalá', 4),
       (N'San Ignacio', 4),
       (N'Nueva Concepción', 4),
       (N'Tejutla', 4),
       (N'La Reina', 4),
       (N'Agua Caliente', 4),
       (N'Dulce Nombre de María', 4),
       (N'El Paraíso', 4),
       (N'San Fernando', 4),
       (N'San Francisco Morazán', 4),
       (N'San Rafael', 4),
       (N'Santa Rita', 4),
       (N'Chalatenango', 4),
       (N'Arcatao', 4),
       (N'Azacualpa', 4),
       (N'Comalapa', 4),
       (N'Concepción Quezaltepeque', 4),
       (N'El Carrizal', 4),
       (N'La Laguna', 4),
       (N'Las Vueltas', 4),
       (N'Nombre de Jesús', 4),
       (N'Nueva Trinidad', 4),
       (N'Ojos de Agua', 4),
       (N'Potonico', 4),
       (N'San Antonio de la Cruz', 4),
       (N'San Antonio Los Ranchos', 4),
       (N'San Francisco Lempa', 4),
       (N'San Isidro Labrador', 4),
       (N'San José Cancasque', 4),
       (N'San José Las Flores', 4),
       (N'San Luis del Carmen', 4),
       (N'San Miguel de Mercedes', 4);

-- La Libertad (IdDepartamento = 5) — 22 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Quezaltepeque', 5),
       (N'San Matías', 5),
       (N'San Pablo Tacachico', 5),
       (N'San Juan Opico', 5),
       (N'Ciudad Arce', 5),
       (N'Colón', 5),
       (N'Jayaque', 5),
       (N'Sacacoyo', 5),
       (N'Tepecoyo', 5),
       (N'Talnique', 5),
       (N'Antiguo Cuscatlán', 5),
       (N'Huizúcar', 5),
       (N'Nuevo Cuscatlán', 5),
       (N'San José Villanueva', 5),
       (N'Zaragoza', 5),
       (N'Chiltiupán', 5),
       (N'Jicalapa', 5),
       (N'La Libertad', 5),
       (N'Tamanique', 5),
       (N'Teotepeque', 5),
       (N'Comasagua', 5),
       (N'Santa Tecla', 5);

-- San Salvador (IdDepartamento = 6) — 19 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Aguilares', 6),
       (N'El Paisnal', 6),
       (N'Guazapa', 6),
       (N'Apopa', 6),
       (N'Nejapa', 6),
       (N'Ilopango', 6),
       (N'San Martín', 6),
       (N'Soyapango', 6),
       (N'Tonacatepeque', 6),
       (N'Ayutuxtepeque', 6),
       (N'Mejicanos', 6),
       (N'San Salvador', 6),
       (N'Cuscatancingo', 6),
       (N'Ciudad Delgado', 6),
       (N'Panchimalco', 6),
       (N'Rosario de Mora', 6),
       (N'San Marcos', 6),
       (N'Santo Tomás', 6),
       (N'Santiago Texacuangos', 6);

-- Cuscatlán (IdDepartamento = 7) — 16 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Suchitoto', 7),
       (N'San José Guayabal', 7),
       (N'Oratorio de Concepción', 7),
       (N'San Bartolomé Perulapía', 7),
       (N'San Pedro Perulapán', 7),
       (N'Cojutepeque', 7),
       (N'San Rafael Cedros', 7),
       (N'Candelaria', 7),
       (N'Monte San Juan', 7),
       (N'El Carmen', 7),
       (N'San Cristóbal', 7),
       (N'Santa Cruz Michapa', 7),
       (N'San Ramón', 7),
       (N'El Rosario', 7),
       (N'Santa Cruz Analquito', 7),
       (N'Tenancingo', 7);

-- La Paz (IdDepartamento = 8) — 22 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Cuyultitán', 8),
       (N'Olocuilta', 8),
       (N'San Juan Talpa', 8),
       (N'San Luis Talpa', 8),
       (N'San Pedro Masahuat', 8),
       (N'Tapalhuaca', 8),
       (N'San Francisco Chinameca', 8),
       (N'El Rosario', 8),
       (N'Jerusalén', 8),
       (N'Mercedes La Ceiba', 8),
       (N'Paraíso de Osorio', 8),
       (N'San Antonio Masahuat', 8),
       (N'San Emigdio', 8),
       (N'San Juan Tepezontes', 8),
       (N'San Luis La Herradura', 8),
       (N'San Miguel Tepezontes', 8),
       (N'San Pedro Nonualco', 8),
       (N'Santa María Ostuma', 8),
       (N'Santiago Nonualco', 8),
       (N'San Juan Nonualco', 8),
       (N'San Rafael Obrajuelo', 8),
       (N'Zacatecoluca', 8);

-- Cabañas (IdDepartamento = 9) — 9 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Sensuntepeque', 9),
       (N'Victoria', 9),
       (N'Dolores', 9),
       (N'Guacotecti', 9),
       (N'San Isidro', 9),
       (N'Ilobasco', 9),
       (N'Tejutepeque', 9),
       (N'Jutiapa', 9),
       (N'Cinquera', 9);

-- San Vicente (IdDepartamento = 10) — 13 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Apastepeque', 10),
       (N'Santa Clara', 10),
       (N'San Ildefonso', 10),
       (N'San Esteban Catarina', 10),
       (N'San Sebastián', 10),
       (N'San Lorenzo', 10),
       (N'Santo Domingo', 10),
       (N'San Vicente', 10),
       (N'Guadalupe', 10),
       (N'Verapaz', 10),
       (N'Tepetitán', 10),
       (N'Tecoluca', 10),
       (N'San Cayetano Istepeque', 10);

-- Usulután (IdDepartamento = 11) — 23 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Santiago de María', 11),
       (N'Alegría', 11),
       (N'Berlín', 11),
       (N'Mercedes Umaña', 11),
       (N'Jucuapa', 11),
       (N'El Triunfo', 11),
       (N'Estanzuelas', 11),
       (N'San Buenaventura', 11),
       (N'Nueva Granada', 11),
       (N'Usulután', 11),
       (N'Jucuarán', 11),
       (N'San Dionisio', 11),
       (N'Concepción Batres', 11),
       (N'Santa María', 11),
       (N'Ozatlán', 11),
       (N'Tecapán', 11),
       (N'Santa Elena', 11),
       (N'California', 11),
       (N'Ereguayquín', 11),
       (N'Jiquilisco', 11),
       (N'Puerto El Triunfo', 11),
       (N'San Agustín', 11),
       (N'San Francisco Javier', 11);

-- San Miguel (IdDepartamento = 12) — 20 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Ciudad Barrios', 12),
       (N'Sesori', 12),
       (N'Nuevo Edén de San Juan', 12),
       (N'San Gerardo', 12),
       (N'San Luis de la Reina', 12),
       (N'Carolina', 12),
       (N'San Antonio del Mosco', 12),
       (N'Chapeltique', 12),
       (N'San Miguel', 12),
       (N'Comacarán', 12),
       (N'Uluazapa', 12),
       (N'Moncagua', 12),
       (N'Quelepa', 12),
       (N'Chirilagua', 12),
       (N'Chinameca', 12),
       (N'Nueva Guadalupe', 12),
       (N'Lolotique', 12),
       (N'San Jorge', 12),
       (N'San Rafael Oriente', 12),
       (N'El Tránsito', 12);

-- Morazán (IdDepartamento = 13) — 26 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Arambala', 13),
       (N'Cacaopera', 13),
       (N'Corinto', 13),
       (N'El Rosario', 13),
       (N'Joateca', 13),
       (N'Jocoaitique', 13),
       (N'Meanguera', 13),
       (N'Perquín', 13),
       (N'San Fernando', 13),
       (N'San Isidro', 13),
       (N'Torola', 13),
       (N'Chilanga', 13),
       (N'Delicias de Concepción', 13),
       (N'El Divisadero', 13),
       (N'Gualococti', 13),
       (N'Guatajiagua', 13),
       (N'Jocoro', 13),
       (N'Lolotiquillo', 13),
       (N'Osicala', 13),
       (N'San Carlos', 13),
       (N'San Francisco Gotera', 13),
       (N'San Simón', 13),
       (N'Sensembra', 13),
       (N'Sociedad', 13),
       (N'Yamabal', 13),
       (N'Yoloaiquín', 13);

-- La Unión (IdDepartamento = 14) — 18 distritos
INSERT INTO Distrito (Nombre, IdDepartamento)
VALUES (N'Anamorós', 14),
       (N'Bolívar', 14),
       (N'Concepción de Oriente', 14),
       (N'El Sauce', 14),
       (N'Lislique', 14),
       (N'Nueva Esparta', 14),
       (N'Pasaquina', 14),
       (N'Polorós', 14),
       (N'San José La Fuente', 14),
       (N'Santa Rosa de Lima', 14),
       (N'Conchagua', 14),
       (N'El Carmen', 14),
       (N'Intipucá', 14),
       (N'La Unión', 14),
       (N'Meanguera del Golfo', 14),
       (N'San Alejo', 14),
       (N'Yayantique', 14),
       (N'Yucuaiquín', 14);

-- ------------------------------------------------------------
-- Categorías de habilidades
-- El admin puede agregar más desde el panel de administración.
-- ------------------------------------------------------------
INSERT INTO CategoriaHabilidad (Nombre, Descripcion)
VALUES (N'Programación', N'Lenguajes y paradigmas de programación'),
       ('Base de Datos', 'Gestores y modelado de datos'),
       ('Frameworks Backend', 'Frameworks del lado del servidor'),
       ('Frameworks Frontend', 'Frameworks del lado del cliente'),
       ('Herramientas DevOps', 'CI/CD, contenedores, nube'),
       ('Habilidades Blandas', N'Comunicación, trabajo en equipo, liderazgo');

-- ------------------------------------------------------------
-- Habilidades iniciales
-- ------------------------------------------------------------
INSERT INTO Habilidad (Nombre, Descripcion, IdCategoriaHabilidad)
VALUES ('Java', 'Lenguaje Java SE/EE', 1),
       ('Python', 'Lenguaje Python', 1),
       ('JavaScript', 'JavaScript ES6+', 1),
       ('TypeScript', 'TypeScript', 1),
       ('SQL', 'Lenguaje de consulta SQL', 2),
       ('SQL Server', 'Microsoft SQL Server', 2),
       ('MySQL', 'MySQL / MariaDB', 2),
       ('Spring Boot', 'Framework Spring Boot', 3),
       ('Angular', 'Angular 17+', 4),
       ('React', 'React 18+', 4),
       ('Docker', 'Contenedores con Docker', 5),
       ('Git', 'Control de versiones Git', 5),
       ('Trabajo en equipo', N'Colaboración efectiva', 6),
       (N'Comunicación', N'Comunicación oral y escrita', 6);

-- ------------------------------------------------------------
-- Idiomas iniciales
-- ------------------------------------------------------------
INSERT INTO Idioma (Nombre)
VALUES (N'Español'),
       (N'Inglés'),
       (N'Francés'),
       (N'Portugués');
GO

-- ============================================================
--  Seguridad: Roles, Privilegios y asignaciones RolPrivilegio
-- ============================================================

-- ------------------------------------------------------------
-- Roles del sistema
-- ------------------------------------------------------------
INSERT INTO Rol (Nombre, Descripcion)
VALUES ('ADMIN', N'Administrador del sistema con acceso total'),
       ('POSTULANTE', N'Usuario que busca empleo y aplica a ofertas de trabajo'),
       ('EMPRESA', N'Empresa que publica y gestiona ofertas de trabajo');
GO

-- ------------------------------------------------------------
-- Privilegios del sistema
-- Nombre: clave usada en @PreAuthorize y en el JWT
-- NombreMenu: texto visible en el frontend
-- Ruta: ruta del frontend (NULL si es acción pura sin pantalla)
-- ------------------------------------------------------------
INSERT INTO Privilegio (Nombre, NombreMenu, Ruta)
VALUES ('GESTIONAR_USUARIOS',  N'Gestión de Usuarios',  '/admin/usuarios'),
       ('GESTIONAR_ROLES',     N'Gestión de Roles',     '/admin/roles'),
       ('GESTIONAR_CATALOGOS', N'Gestión de Catálogos', '/admin/catalogos'),
       ('VER_REPORTES',        N'Reportes',             '/admin/reportes'),
       ('GESTIONAR_OFERTAS',   N'Mis Ofertas',          '/ofertas/gestionar'),
       ('VER_OFERTAS',         N'Ofertas de Trabajo',   '/ofertas'),
       ('APLICAR_OFERTA',      N'Aplicar a Oferta',     NULL),
       ('VER_ASPIRANTES',      N'Aspirantes',           '/ofertas/aspirantes'),
       ('GESTIONAR_PERFIL',    N'Mi Perfil',            '/perfil');
GO

-- ------------------------------------------------------------
-- RolPrivilegio: asignación de privilegios por rol
-- ADMIN: todos los privilegios
-- ------------------------------------------------------------
INSERT INTO RolPrivilegio (IdRol, IdPrivilegio)
SELECT r.IdRol, p.IdPrivilegio
FROM Rol r
         CROSS JOIN Privilegio p
WHERE r.Nombre = 'ADMIN';
GO

-- POSTULANTE: ver ofertas, aplicar, gestionar su perfil
INSERT INTO RolPrivilegio (IdRol, IdPrivilegio)
SELECT r.IdRol, p.IdPrivilegio
FROM Rol r
         JOIN Privilegio p ON p.Nombre IN ('VER_OFERTAS', 'APLICAR_OFERTA', 'GESTIONAR_PERFIL')
WHERE r.Nombre = 'POSTULANTE';
GO

-- EMPRESA: gestionar sus ofertas, ver aspirantes, gestionar su perfil, ver ofertas
INSERT INTO RolPrivilegio (IdRol, IdPrivilegio)
SELECT r.IdRol, p.IdPrivilegio
FROM Rol r
         JOIN Privilegio p ON p.Nombre IN ('VER_OFERTAS', 'GESTIONAR_OFERTAS', 'VER_ASPIRANTES', 'GESTIONAR_PERFIL')
WHERE r.Nombre = 'EMPRESA';
GO
