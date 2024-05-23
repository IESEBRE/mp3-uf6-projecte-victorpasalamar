-- Poseu el codi dels procediments/funcions emmagatzemats, triggers, ..., usats al projecte

-- Trigger per a crear el id dels jefes automaticament
create or replace TRIGGER crear_id_automatic_jefes 
BEFORE INSERT ON JEFES 
FOR EACH ROW 
DECLARE 
max_id_jefe NUMBER(5);
BEGIN
SELECT NVL(MAX(ID_JEFE), 0) INTO max_id_jefe 
FROM JEFES;
:NEW.ID_JEFE:=max_id_jefe+1; 
END;

--Procediment usat per a crear les taules usades al programa:
create or replace PROCEDURE crear_taules AUTHID CURRENT_USER IS
    tabla_jefes_count INTEGER;
    tabla_dificultat_count INTEGER;
BEGIN

    SELECT COUNT(*) INTO tabla_jefes_count
    FROM all_tables
    WHERE table_name = 'JEFES' AND owner = USER;


    IF tabla_jefes_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE TABLE JEFES (
            ID_JEFE NUMBER(5) PRIMARY KEY,
            NOM_JEFE VARCHAR2(50),
            VIDA NUMBER(5),
            NUMATACS NUMBER(5),
            LOCACIO VARCHAR2(30)
        )';
    END IF;


    SELECT COUNT(*) INTO tabla_dificultat_count
    FROM all_tables
    WHERE table_name = 'DIFICULTAT' AND owner = USER;


    IF tabla_dificultat_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE TABLE DIFICULTAT (
            ID_JEFE NUMBER(5),
            DIFICULTAT NUMBER(1),
            FOREIGN KEY (ID_JEFE) REFERENCES JEFES(ID_JEFE)
        )';
    END IF;
END;

--Procediments i/o funcions per a tractar les dades (insertar i borrar)
create or replace FUNCTION insertar_jefe (
   p_nom IN VARCHAR2,
   p_vida IN NUMBER,
   p_dificultat IN NUMBER,
   p_atacs IN NUMBER,
   p_localitzacio IN VARCHAR2
) RETURN NUMBER IS
BEGIN
    INSERT INTO JEFES (NOM_JEFE, VIDA, NUMATACS, LOCACIO) VALUES (p_nom, p_vida, p_atacs, p_localitzacio);
    INSERT INTO DIFICULTAT (ID_JEFE, DIFICULTAT) VALUES ((SELECT max(ID_JEFE) FROM JEFES), p_dificultat);
    RETURN 1; -- Indica que la función s'ha executat correctament
END;

create or replace PROCEDURE borrar_jefe (
   p_nom IN VARCHAR2,
   p_vida IN NUMBER,
   p_dificultat IN NUMBER,
   p_atacs IN NUMBER,
   p_localitzacio IN VARCHAR2
) IS
BEGIN
    DELETE FROM DIFICULTAT where ID_JEFE=(SELECT id_jefe from jefes where NOM_JEFE=p_nom AND VIDA=p_vida AND NUMATACS=p_atacs AND LOCACIO=p_localitzacio);
    DELETE FROM JEFES  where NOM_JEFE=p_nom AND VIDA=p_vida AND NUMATACS=p_atacs AND LOCACIO=p_localitzacio;
END;
