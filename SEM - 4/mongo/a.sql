CREATE TABLE especies (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR2(50) NOT NULL UNIQUE,
    pais_de_origem VARCHAR2(50) NOT NULL
);

-- INSERT INTO especies (nome, pais_de_origem) VALUES (
--     'ARARA AZUL', 'BRASIL'
-- );

-- INSERT INTO especies (nome, pais_de_origem) VALUES ('CAPIPAPI', 'BRASIL');
-- INSERT INTO especies(nome, pais_de_origem) VALUES ('José', 'AFRICA DO SUL');
COMMIT;

SELECT * FROM ESPECIES WHERE PAIS_DE_ORIGEM='BRASIL';


DECLARE 
    r_arara ESPECIES%ROWTYPE;
BEGIN 
    
    SELECT * INTO r_arara
    FROM ESPECIES WHERE NOME = 'ARARA AZUL';
    DBMS_OUTPUT.PUT_LINE(r_arara.id);

end;/
