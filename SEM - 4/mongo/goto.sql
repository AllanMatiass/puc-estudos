


SET SERVEROUTPUT ON;

declare
    v_idade NATURAL := 20;
    c_mensalidade CONSTANT NUMBER (5, 2) := 100.00;
    v_mensalidade_final NUMBER(5,2);

begin
    DBMS_OUTPUT.PUT_LINE('SEM GOTO');

    IF (v_idade IS NULL OR v_idade <> TRUNC(v_idade)) then
        DBMS_OUTPUT.PUT_LINE('Idade invalida');
    
    else

        IF v_idade <= 12 then
            v_mensalidade_final := c_mensalidade * 0.5;
        
        ELSIF v_idade >= 60 THEN
            v_mensalidade_final := c_mensalidade * 0.8;
        else
            v_mensalidade_final := c_mensalidade;
        END IF;

    END IF;
    DBMS_OUTPUT.PUT_LINE('mensalidade: ' || TO_CHAR(v_mensalidade_final, 'FM990D00'));
end;/

-- GOTO nome_rotulo


declare
    v_idade POSITIVEN := 10;
    c_mensalidade CONSTANT NUMBER (5, 2) := 100.00;
    v_mensalidade_final NUMBER(5,2);

begin
    DBMS_OUTPUT.PUT_LINE('COM GOTO');

    IF (v_idade IS NULL OR v_idade <> TRUNC(v_idade)) then
        DBMS_OUTPUT.PUT_LINE('Idade invalida');
        GOTO fim;
    END IF;



    IF v_idade <= 12 then
        GOTO mensalidade_infantil;

    ELSIF v_idade >= 60 then
        GOTO mensalidade_idoso;
    
    END IF;

    
    v_mensalidade_final := c_mensalidade;

    GOTO exibir_mensalidade;

    <<mensalidade_infantil>>
    v_mensalidade_final := c_mensalidade * 0.5;
    GOTO exibir_mensalidade;

    <<mensalidade_idoso>>
    v_mensalidade_final := c_mensalidade * 0.8;
    GOTO exibir_mensalidade;

    <<exibir_mensalidade>>
    DBMS_OUTPUT.PUT_LINE('Mensalidade: R$' || TO_CHAR(v_mensalidade_final, 'FM990D00'));

    <<fim>>
    NULL;
END;
/
