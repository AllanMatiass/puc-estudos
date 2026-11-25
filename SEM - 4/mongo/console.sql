SET serveroutput ON;
-- declare 
--     c_pi CONSTANT NUMBER(5,4) := 3.1415;

-- begin

--     DBMS_OUTPUT.PUT_LINE(c_pi);
-- end;

-- /

-- Manipulação de dados em PLSQL usando cursores

-- CREATE TABLE CONSOLES(
--     ID NUMBER(4) NOT NULL PRIMARY KEY,
--     NAME VARCHAR2(20) NOT NULL,
--     BRAND VARCHAR2(20) NOT NULL
-- );/


-- INSERT INTO CONSOLES (ID, NAME, BRAND) VALUES (1, 'Atari 2600', 'Atari');
-- /
-- INSERT INTO CONSOLES (ID, NAME, BRAND) VALUES (2, 'Mega Drive Genesis', 'Sega');
-- /
-- INSERT INTO CONSOLES (ID, NAME, BRAND) VALUES (3, 'Super Nintendo', 'Nintendo');
-- /

-- INSERT INTO CONSOLES(ID, NAME, BRAND) VALUES (4, 'Master System 1', 'Sega');
-- /


-- INSERT INTO CONSOLES(ID, NAME, BRAND) VALUES (5, 'Nintendo Wii', 'Nintendo');
-- /


-- INSERT INTO CONSOLES(ID, NAME, BRAND) VALUES (6, 'Atari Classic', 'Atari');
-- /
-- commit;
-- /


DECLARE 
    v_console CONSOLES.NAME%TYPE;
    CURSOR c CONSOLES;
begin
    SELECT NAME INTO v_console FROM CONSOLES WHERE BRAND LIKE 'Sega';
    DBMS_OUTPUT.PUT_LINE(v_console);
END;
/

DECLARE 
    r_console CONSOLES%ROWTYPE;
begin
    SELECT * INTO r_console FROM CONSOLES WHERE BRAND = 'Sega';
    DBMS_OUTPUT.PUT_LINE(r_console.NAME);
END;
/

-- Cursor explicito com for

declare
    CURSOR c IS SELECT * FROM CONSOLES;
    v_counter POSITIVEN := 1;
begin
    for r IN c 
    loop
        DBMS_OUTPUT.PUT_LINE(v_counter || ' | ' || r.NAME || ' | ' || r.BRAND);
        v_counter := v_counter + 1;
    END LOOP;
END;
/

-- CU PARAMETRIZADO
declare
    CURSOR cu(p_brand VARCHAR2) IS SELECT * FROM CONSOLES WHERE BRAND = p_brand;
    v_counter POSITIVEN := 1;
begin
    for r IN cu('Atari') 
    loop
        DBMS_OUTPUT.PUT_LINE(v_counter || ' | ' || r.NAME || ' | ' || r.BRAND);
        v_counter := v_counter + 1;
    END LOOP;
END;

-- 
-- AGRUPAR POR BRAND USANDO OPEN FETCH CLOSE
declare
    CURSOR cu IS SELECT * FROM CONSOLES GROUP BY BRAND;
    v_counter POSITIVEN := 1;
begin
    for r IN cu
    loop
        DBMS_OUTPUT.PUT_LINE(v_counter || ' | ' || r.NAME || ' | ' || r.NOME);
        v_counter := v_counter + 1;
    END LOOP;
END;

/
