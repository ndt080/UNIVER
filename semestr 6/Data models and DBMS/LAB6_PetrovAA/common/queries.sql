/*
    [DATABASE: POSTRESQL]
    ЛАБОРАТОРНАЯ РАБОТА №1. ОБЩАЯ ЧАСТЬ
    13 ГРУППА 3 КУРС
    ПЕТРОВ АНДРЕЙ АЛЕКСАНДРОВИЧ
 */


--Задание 1
ALTER TABLE SALARY
  ADD IF NOT EXISTS TAX NUMERIC;



-- 04. Создайте функцию, вычисляющую налог на зарплату за всё время начислений для
-- конкретного сотрудника. В качестве параметров передать процент налога (до 20000, до
-- 30000, выше 30000, номер сотрудника).  Возвращаемое значение – суммарный налог.
create or replace FUNCTION FTAX_PARAM_LESS(
  EMPID NUMBER,
  UNDER_20k NUMBER,
  OVER_20k NUMBER,
  OVER_30k NUMBER) RETURN NUMBER AS CURSOR CUR IS
SELECT EMPNO, SALVALUE, TAX, YEAR, MONTH
FROM SALARY
WHERE EMPNO = EMPID;
SUMSAL NUMBER(16);
RESULT NUMBER(16);
BEGIN
RESULT := 0;
FOR R IN CUR
        LOOP
SELECT SUM(SALVALUE)
INTO SUMSAL
FROM SALARY S
WHERE S.EMPNO = R.EMPNO
  AND S.MONTH < R.MONTH
  AND S.YEAR = R.YEAR;

RESULT := RESULT +
                      CASE
                          WHEN SUMSAL < 20000 THEN R.SALVALUE * UNDER_20k
                          WHEN SUMSAL < 30000 THEN R.SALVALUE * OVER_20k
                          ELSE R.SALVALUE * OVER_30k
END;

END LOOP;
RETURN RESULT;
END FTAX_PARAM_LESS;

-- вызов
begin
DBMS_OUTPUT.PUT_LINE(FTAX_PARAM_LESS(7790, 1, 2, 3));
end;

-- 05. Создайте процедуру, вычисляющую суммарный налог на зарплату сотрудника за
-- всё время начислений. В качестве параметров передать процент налога (до 20000, до
-- 30000, выше 30000, номер сотрудника).
create or replace PROCEDURE TAX_PARAM(EMPID NUMBER) AS CURSOR CUR IS
SELECT EMPNO, SALVALUE, TAX, YEAR, MONTH
FROM SALARY
WHERE EMPNO = EMPID FOR UPDATE OF TAX;
SUMSAL NUMBER(16);
BEGIN
FOR R IN CUR
        LOOP
SELECT SUM(SALVALUE)
INTO SUMSAL
FROM SALARY S
WHERE S.EMPNO = R.EMPNO
  AND S.MONTH < R.MONTH
  AND S.YEAR = R.YEAR;

UPDATE SALARY
SET TAX = CASE
            WHEN SUMSAL < 20000 THEN R.SALVALUE * 0.09
            WHEN SUMSAL < 30000 THEN R.SALVALUE * 0.12
            ELSE R.SALVALUE * 0.15 END

WHERE EMPNO = R.EMPNO
  AND MONTH = R.MONTH
  AND YEAR = R.YEAR;
END LOOP;
COMMIT;
END TAX_PARAM;

-- 06. Создайте пакет, включающий в свой состав процедуру вычисления налога для всех
-- сотрудников, процедуру вычисления налогов для отдельного сотрудника,
-- идентифицируемого своим номером, функцию вычисления суммарного налога на
-- зарплату сотрудника за всё время начислений.
create
or
replace
PACKAGE TAX_PCG AS
    PROCEDURE TAX_SIMPLE_LOOP_IF;
PROCEDURE TAX_PARAM(EMPID NUMBER);
FUNCTION FTAX_PARAM_LESS(
        EMPID NUMBER,
        UNDER_20k NUMBER,
        OVER_20k NUMBER,
        OVER_30k NUMBER) RETURN NUMBER;
END TAX_PCG;

create
or
replace
PACKAGE BODY TAX_PCG AS
    PROCEDURE TAX_SIMPLE_LOOP_IF AS
        SUMSAL NUMBER(16);
    BEGIN
FOR R IN (SELECT * FROM SALARY)
            LOOP
SELECT SUM(SALVALUE)
INTO SUMSAL
FROM SALARY S
WHERE S.EMPNO = R.EMPNO
  AND S.MONTH < R.MONTH
  AND S.YEAR = R.YEAR;

IF SUMSAL < 20000 THEN
UPDATE SALARY
SET TAX = R.SALVALUE * 0.09
WHERE EMPNO = R.EMPNO
  AND MONTH = R.MONTH
  AND YEAR = R.YEAR;
ELSIF SUMSAL < 30000 THEN
UPDATE SALARY
SET TAX = R.SALVALUE * 0.12
WHERE EMPNO = R.EMPNO
  AND MONTH = R.MONTH
  AND YEAR = R.YEAR;
ELSE
UPDATE SALARY
SET TAX = R.SALVALUE * 0.15
WHERE EMPNO = R.EMPNO
  AND MONTH = R.MONTH
  AND YEAR = R.YEAR;
END IF;
END LOOP;
COMMIT;
END;

PROCEDURE TAX_PARAM(EMPID NUMBER) AS

        CURSOR CUR IS
SELECT EMPNO, SALVALUE, TAX, YEAR, MONTH
FROM SALARY
WHERE EMPNO = EMPID FOR UPDATE OF TAX;
SUMSAL NUMBER(16);
    BEGIN
LOOP
            R IN CUR LOOP
SELECT SUM(SALVALUE)
INTO SUMSAL
FROM SALARY S
WHERE S.EMPNO = R.EMPNO
  AND S.MONTH < R.MONTH
  AND S.YEAR = R.YEAR;

UPDATE SALARY
SET TAX = CASE
            WHEN SUMSAL < 20000 THEN R.SALVALUE * 0.09
            WHEN SUMSAL < 30000 THEN R.SALVALUE * 0.12
            ELSE R.SALVALUE * 0.15 END

WHERE EMPNO = R.EMPNO
  AND MONTH = R.MONTH
  AND YEAR = R.YEAR;
END LOOP;
COMMIT;
END TAX_PARAM;

FUNCTION FTAX_PARAM_LESS(
        EMPID NUMBER,
        UNDER_20k NUMBER,
        OVER_20k NUMBER,
        OVER_30k NUMBER) RETURN NUMBER AS

        CURSOR CUR IS
SELECT EMPNO, SALVALUE, TAX, YEAR, MONTH
FROM SALARY
WHERE EMPNO = EMPID;
SUMSAL NUMBER(16);
RESULT NUMBER(16);
    BEGIN
RESULT := 0;
FOR R IN CUR
            LOOP
SELECT SUM(SALVALUE)
INTO SUMSAL
FROM SALARY S
WHERE S.EMPNO = R.EMPNO
  AND S.MONTH < R.MONTH
  AND S.YEAR = R.YEAR;

RESULT := RESULT +
                          CASE
                              WHEN SUMSAL < 20000 THEN R.SALVALUE * UNDER_20k
                              WHEN SUMSAL < 30000 THEN R.SALVALUE * OVER_20k
                              ELSE R.SALVALUE * OVER_30k
END;

END LOOP;
RETURN RESULT;
END FTAX_PARAM_LESS;
END TAX_PCG;

-- вызов
begin
DBMS_OUTPUT.PUT_LINE(TAX_PCG.FTAX_PARAM_LESS(7790, 1, 2, 3));
end;

-- 07. Создайте триггер, действующий при обновлении данных в таблице SALARY. А
-- именно, если происходит обновление поля SALVALUE, то при назначении новой
-- зарплаты, меньшей чем должностной оклад (таблица JOB, поле MINSALARY), изменение
-- не вносится и сохраняется старое значение, если новое значение зарплаты больше
-- должностного оклада, то изменение вносится.
create
or
replace
TRIGGER CHECK_SALARY
    BEFORE
UPDATE OF SALVALUE
ON SALARY FOR EACH ROW
DECLARE CURSOR CUR_MINSALARY(EMPID CAREER.EMPNO%TYPE) IS
SELECT MINSALARY
FROM JOB
WHERE JOBNO IN (SELECT JOBNO FROM CAREER WHERE EMPID = EMPNO AND ENDDATE IS NULL);
MINSALARY_VALUE JOB.MINSALARY%TYPE;
BEGIN
OPEN CUR_MINSALARY(:NEW.EMPNO);
FETCH CUR_MINSALARY INTO MINSALARY_VALUE;
IF :NEW.SALVALUE < MINSALARY_VALUE THEN
        :NEW.SALVALUE := :OLD.SALVALUE;
END IF;
CLOSE CUR_MINSALARY;
END CHECK_SALARY;

-- 08. Создайте триггер, действующий при удалении записи из таблицы CAREER. Если в
-- удаляемой строке поле ENDDATE содержит NULL, то запись не удаляется, в противном
-- случае удаляется.
create
or
replace
TRIGGER HANDLE_DELETE_FOR_WORKERS
    BEFORE
DELETE
ON CAREER FOR EACH ROW WHEN (OLD.ENDDATE IS NULL)
BEGIN
RAISE_APPLICATION_ERROR
  (-20001, 'Worker can not be deleted');
END HANDLE_DELETE_FOR_WORKERS;

-- 09. Создайте триггер, действующий на добавление или изменение данных в таблице
-- EMP. Если во вставляемой или изменяемой строке поле BIRTHDATE содержит NULL, то
-- после вставки или изменения должно быть выдано сообщение ‘BERTHDATE is NULL’.
-- Если во вставляемой или изменяемой строке поле BIRTHDATE содержит дату ранее ‘01-
-- 01-1940’, то должно быть выдано сообщение ‘pensioner’. Во вновь вставляемой строке имя
-- служащего должно быть приведено к заглавным буквам.
create
or
replace
TRIGGER ON_EMP_INSERT_UPDATE
    BEFORE
INSERT OR
UPDATE
ON EMP FOR EACH ROW
BEGIN
IF :NEW.BIRTHDATE IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('BIRTHDATE IS NULL');
END IF;

IF :NEW.BIRTHDATE < to_date('01-01-1940', 'dd-mm-yyyy') THEN
        DBMS_OUTPUT.PUT_LINE('PENTIONA');
END IF;

:NEW.EMPNAME := UPPER(:NEW.EMPNAME);
END ON_EMP_INSERT_UPDATE;

-- 10. Создайте программу изменения типа заданной переменной из символьного типа
-- (VARCHAR2) в числовой тип (NUMBER). Программа должна содержать раздел
-- обработки исключений. Обработка должна заключаться в выдаче сообщения ‘ERROR:
-- argument is not a number’ . Исключительная ситуация возникает при задании строки в виде
-- числа с запятой, разделяющей дробную и целую части.
create or replace FUNCTION VARCHAR2_TO_NUMBER(str in VARCHAR2) return NUMBER IS
BEGIN
RETURN CAST(str AS NUMBER);
EXCEPTION
    WHEN VALUE_ERROR THEN
        RAISE_APPLICATION_ERROR(-20108, 'ERROR: argument is not a number: ' || str);
WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20103, 'Unexpected error');
END VARCHAR2_TO_NUMBER;

-- вызов
BEGIN
DBMS_OUTPUT.PUT_LINE(VARCHAR2_TO_NUMBER('1234123'));
DBMS_OUTPUT.PUT_LINE(VARCHAR2_TO_NUMBER('5.1'));
DBMS_OUTPUT.PUT_LINE(VARCHAR2_TO_NUMBER('4,123'));
END;