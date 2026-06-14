ALTER TABLE PostulanteTelefono DROP CONSTRAINT CK_PostulanteTelefono_Formato;
ALTER TABLE PostulanteTelefono ADD CONSTRAINT CK_PostulanteTelefono_Formato
    CHECK (Telefono LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'
        OR Telefono LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
        OR Telefono LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]');
GO

ALTER TABLE ExperienciaLaboral DROP CONSTRAINT CK_ExperienciaLaboral_Telefono;
ALTER TABLE ExperienciaLaboral ADD CONSTRAINT CK_ExperienciaLaboral_Telefono
    CHECK (TelefonoContacto IS NULL
        OR TelefonoContacto LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'
        OR TelefonoContacto LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
        OR TelefonoContacto LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]');
GO

ALTER TABLE EmpresaTelefono DROP CONSTRAINT CK_EmpresaTelefono_Formato;
ALTER TABLE EmpresaTelefono ADD CONSTRAINT CK_EmpresaTelefono_Formato
    CHECK (Telefono LIKE '[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'
        OR Telefono LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
        OR Telefono LIKE '+[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]');
GO
