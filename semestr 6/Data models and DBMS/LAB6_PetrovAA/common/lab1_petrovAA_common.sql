/*
    [DATABASE: POSTRESQL]
    ЛАБОРАТОРНАЯ РАБОТА №1. ОБЩАЯ ЧАСТЬ
    13 ГРУППА 3 КУРС
    ПЕТРОВ АНДРЕЙ АЛЕКСАНДРОВИЧ
 */


/*
    ЗАДАНИЕ 1.
*/
ALTER TABLE SALARY ADD IF NOT EXISTS TAX NUMERIC;

/*
    ЗАДАНИЕ 2.
    Составьте процедуру вычисления налога и вставки значений в таблицу SALARY:
    a) с помощью простого цикла (loop) с курсором и оператора if;
    b) с помощью простого цикла (loop) с курсором и оператора case;
    c) с помощью курсорного цикла FOR;
    d) с помощью курсора с параметром, передавая номер сотрудника, для которого
       необходимо посчитать налог.
*/

-- a) с помощью простого цикла (loop) с курсором и оператора if;
CREATE OR REPLACE PROCEDURE CALC_TAX_LOOP_IF()
LANGUAGE SQL
AS $$
  DO
  $DO$
    DECLARE
      CUR CURSOR FOR SELECT * FROM SALARY;
      I SALARY;
      SUM_VALUE integer;
    BEGIN
      OPEN CUR;
      LOOP
        FETCH CUR INTO I;

        SELECT SUM(SALVALUE) INTO SUM_VALUE
        FROM SALARY
        WHERE SALARY.EMPNO = I.EMPNO
          AND SALARY.MONTH < I.MONTH
          AND SALARY.YEAR = I.YEAR;

        IF SUM_VALUE < 20000 THEN
            UPDATE SALARY
            SET TAX = I.SALVALUE * 0.09
            WHERE EMPNO = I.EMPNO
              AND MONTH = I.MONTH
              AND YEAR = I.YEAR;
        ELSIF SUM_VALUE < 30000 THEN
            UPDATE SALARY
            SET TAX = I.SALVALUE * 0.12
            WHERE EMPNO = I.EMPNO
              AND MONTH = I.MONTH
              AND YEAR = I.YEAR;
        ELSE
            UPDATE SALARY
            SET TAX = I.SALVALUE * 0.15
            WHERE EMPNO = I.EMPNO
              AND MONTH = I.MONTH
              AND YEAR = I.YEAR;
        END IF;
      END LOOP;
      CLOSE CUR;
      COMMIT;
    END
  $DO$
$$;

--b) с помощью простого цикла (loop) с курсором и оператора case;
CREATE OR REPLACE PROCEDURE CALC_TAX_LOOP_CASE()
LANGUAGE SQL
AS $$
  DO
  $DO$
    DECLARE
      CUR CURSOR FOR SELECT * FROM SALARY;
      I SALARY;
      SUM_VALUE integer;
    BEGIN
      OPEN CUR;
      LOOP
        FETCH CUR INTO I;

        SELECT SUM(SALVALUE) INTO SUM_VALUE
        FROM SALARY
        WHERE SALARY.EMPNO = I.EMPNO
          AND SALARY.MONTH < I.MONTH
          AND SALARY.YEAR = I.YEAR;

        UPDATE SALARY
        SET TAX = CASE
                    WHEN SUM_VALUE < 20000 THEN I.SALVALUE * 0.09
                    WHEN SUM_VALUE < 30000 THEN I.SALVALUE * 0.12
                    ELSE I.SALVALUE * 0.15
                  END
        WHERE EMPNO = I.EMPNO AND MONTH = I.MONTH AND YEAR = I.YEAR;
      END LOOP;
      CLOSE CUR;
      COMMIT;
    END
  $DO$
$$;

-- c) с помощью курсорного цикла FOR;
CREATE OR REPLACE PROCEDURE CALC_TAX_FOR()
LANGUAGE SQL
AS $$
DO
$DO$
  DECLARE
    CUR CURSOR FOR SELECT * FROM SALARY;
    I SALARY;
    SUM_VALUE integer;
  BEGIN
    FOR I IN CUR
    LOOP
        SELECT SUM(SALVALUE) INTO SUM_VALUE
        FROM SALARY
        WHERE SALARY.EMPNO = I.EMPNO
          AND SALARY.MONTH < I.MONTH
          AND SALARY.YEAR = I.YEAR;

        UPDATE SALARY
        SET TAX = CASE
                    WHEN SUM_VALUE < 20000 THEN I.SALVALUE * 0.09
                    WHEN SUM_VALUE < 30000 THEN I.SALVALUE * 0.12
                    ELSE I.SALVALUE * 0.15
                  END
        WHERE EMPNO = I.EMPNO AND MONTH = I.MONTH AND YEAR = I.YEAR;
    END LOOP;
    COMMIT;
  END
$DO$
$$;

-- d) с помощью курсора с параметром, передавая номер сотрудника, для которого необходимо посчитать налог.
CREATE OR REPLACE PROCEDURE CALC_TAX_BY_EMP_ID(EMP_ID integer)
LANGUAGE SQL
AS $$
DO
$DO$
  DECLARE
    CUR CURSOR FOR SELECT * FROM SALARY WHERE EMPNO = EMP_ID;
    I SALARY;
    SUM_VALUE integer;
  BEGIN
    FOR I IN CUR
    LOOP
        SELECT SUM(SALVALUE) INTO SUM_VALUE
        FROM SALARY
        WHERE SALARY.EMPNO = I.EMPNO
          AND SALARY.MONTH < I.MONTH
          AND SALARY.YEAR = I.YEAR;

        UPDATE SALARY
        SET TAX = CASE
                    WHEN SUM_VALUE < 20000 THEN I.SALVALUE * 0.09
                    WHEN SUM_VALUE < 30000 THEN I.SALVALUE * 0.12
                    ELSE I.SALVALUE * 0.15
                  END
        WHERE EMPNO = I.EMPNO AND MONTH = I.MONTH AND YEAR = I.YEAR;
    END LOOP;
    COMMIT;
  END
$DO$
$$;

/*
    ЗАДАНИЕ 4.
    Создайте функцию, вычисляющую налог на зарплату за всё время начислений для конкретного
    сотрудника. В качестве параметров передать процент налога (до 20000, до 30000, выше 30000,
    номер сотрудника).
*/
CREATE OR REPLACE FUNCTION FUNC_CALC_TAX_BY_PERCENT_TAX(EMP_ID integer,
                                                        PERCENT_TAX_UNDER_20K integer,
                                                        PERCENT_TAX_OVER_20K integer,
                                                        PERCENT_TAX_OVER_30K integer) RETURNS integer LANGUAGE plpgsql AS
$$
  DECLARE
    CUR CURSOR FOR SELECT * FROM SALARY WHERE EMPNO = EMP_ID;
    I SALARY;
    SUM_VALUE integer;
    RESULT integer;
  BEGIN
    RESULT := 0;

    FOR I IN CUR
    LOOP
      SELECT SUM(SALVALUE) INTO SUM_VALUE
      FROM SALARY
      WHERE SALARY.EMPNO = I.EMPNO
        AND SALARY.MONTH < I.MONTH
        AND SALARY.YEAR = I.YEAR;

      RESULT := RESULT + CASE
                           WHEN SUM_VALUE < 20000 THEN I.SALVALUE * PERCENT_TAX_UNDER_20K
                           WHEN SUM_VALUE < 30000 THEN I.SALVALUE * PERCENT_TAX_OVER_20K
                           ELSE I.SALVALUE * PERCENT_TAX_OVER_30K
                         END;
    END LOOP;
    RETURN RESULT;
  END;
$$;

/*
    ЗАДАНИЕ 5.
    Создайте процедуру, вычисляющую суммарный налог на зарплату сотрудника за всё время начислений.
    В качестве параметров передать процент налога (до 20000, до 30000, выше 30000, номер сотрудника).
    Возвращаемое значение – суммарный налог.
*/
CREATE OR REPLACE PROCEDURE PROCED_CALC_TAX_BY_PERCENT_TAX(EMP_ID integer,
                                    PERCENT_TAX_UNDER_20K integer,
                                    PERCENT_TAX_OVER_20K integer,
                                    PERCENT_TAX_OVER_30K integer)
LANGUAGE plpgsql
AS $$
DECLARE
  CUR CURSOR FOR SELECT * FROM SALARY WHERE EMPNO = EMP_ID;
  I SALARY;
  SUM_VALUE integer;
  RESULT integer;
BEGIN
  RESULT := 0;

  FOR I IN CUR
  LOOP
      SELECT SUM(SALVALUE) INTO SUM_VALUE
      FROM SALARY
      WHERE SALARY.EMPNO = I.EMPNO
        AND SALARY.MONTH < I.MONTH
        AND SALARY.YEAR = I.YEAR;

      RESULT := RESULT + CASE
                     WHEN SUM_VALUE < 20000 THEN I.SALVALUE * PERCENT_TAX_UNDER_20K
                     WHEN SUM_VALUE < 30000 THEN I.SALVALUE * PERCENT_TAX_OVER_20K
                     ELSE I.SALVALUE * PERCENT_TAX_OVER_30K
                   END;

      UPDATE SALARY
      SET TAX = RESULT
      WHERE EMPNO = I.EMPNO AND MONTH = I.MONTH AND YEAR = I.YEAR;
  END LOOP;
  COMMIT;
END
$$;

/*
    ЗАДАНИЕ 7.
    Создайте триггер, действующий при обновлении данных в таблице SALARY. А именно, если происходит
    обновление поля SALVALUE, то при назначении новой зарплаты, меньшей чем должностной оклад
    (таблица JOB, поле MINSALARY), изменение не вносится и сохраняется старое значение, если
    новое значение зарплаты больше должностного оклада, то изменение вносится.
*/
CREATE OR REPLACE FUNCTION FUNC_CHECK_SALARY() RETURNS TRIGGER LANGUAGE plpgsql AS $func$
  DECLARE
    CUR CURSOR FOR
      SELECT MINSALARY
      FROM JOB
      WHERE JOBNO IN (SELECT JOBNO FROM CAREER WHERE EMPNO = NEW.empno AND ENDDATE IS NULL);
    MIN_SALARY_VALUE integer;
  BEGIN
    OPEN CUR;
    FETCH CUR INTO MIN_SALARY_VALUE;

    IF NEW.SALVALUE < MIN_SALARY_VALUE THEN
      NEW.SALVALUE := OLD.SALVALUE;
    END IF;
    CLOSE CUR;
  END;
$func$;

DROP TRIGGER IF EXISTS TRIGGER_CHECK_SALARY ON SALARY;
CREATE TRIGGER TRIGGER_CHECK_SALARY BEFORE UPDATE OF SALVALUE ON SALARY
FOR EACH ROW EXECUTE PROCEDURE FUNC_CHECK_SALARY();

/*
    ЗАДАНИЕ 8.
    Создайте триггер, действующий при удалении записи из таблицы CAREER. Если в удаляемой строке
    поле ENDDATE содержит NULL, то запись не удаляется, в противном случае удаляется.
*/
CREATE OR REPLACE FUNCTION FUNC_HANDLE_DELETE_CAREER() RETURNS TRIGGER LANGUAGE plpgsql AS $func$
  BEGIN
    IF OLD.ENDDATE IS NULL THEN
      RAISE EXCEPTION 'Worker can not be deleted';
    END IF;
    RETURN new;
  END;
$func$;

DROP TRIGGER IF EXISTS TRIGGER_HANDLE_DELETE_CAREER ON CAREER;
CREATE TRIGGER TRIGGER_HANDLE_DELETE_CAREER
  BEFORE DELETE ON CAREER
  FOR EACH ROW
  EXECUTE PROCEDURE FUNC_HANDLE_DELETE_CAREER();

/*
     ЗАДАНИЕ 9.
     Создайте триггер, действующий на добавление или изменение данных в таблице EMP.
     Если во вставляемой или изменяемой строке поле BIRTHDATE содержит NULL, то после
     вставки или изменения должно быть выдано сообщение ‘BERTHDATE is NULL’. Если во
     вставляемой или изменяемой строке поле BIRTHDATE содержит дату ранее ‘01-01-1940’, то
     должно быть выдано сообщение ‘pensioner’. Во вновь вставляемой строке имя служащего должно
     быть приведено к заглавным буквам.
 */
CREATE OR REPLACE FUNCTION FUNC_EMP_INSERT_UPDATE() RETURNS TRIGGER LANGUAGE plpgsql AS $func$
  BEGIN
    IF NEW.BIRTHDATE IS NULL THEN
      RAISE NOTICE 'BIRTHDATE IS NULL';
    END IF;

    IF NEW.BIRTHDATE < to_date('01-01-1940', 'dd-mm-yyyy') THEN
      RAISE NOTICE 'pensioner';
    END IF;

    NEW.EMPNAME := UPPER(NEW.EMPNAME);
  END;
$func$;

DROP TRIGGER IF EXISTS TRIGGER_EMP_INSERT_UPDATE ON EMP;
CREATE TRIGGER TRIGGER_EMP_INSERT_UPDATE
  BEFORE INSERT OR UPDATE ON EMP
  FOR EACH ROW
  EXECUTE PROCEDURE FUNC_EMP_INSERT_UPDATE();

/*
    ЗАДАНИЕ 10.
    Создайте программу изменения типа заданной переменной из символьного типа (VARCHAR2) в числовой
    тип (NUMBER). Программа должна содержать раздел обработки исключений. Обработка должна заключаться
    в выдаче сообщения ‘ERROR: argument is not a number’ . Исключительная ситуация возникает при
    задании строки в виде числа с запятой, разделяющей дробную и целую части.
*/
CREATE OR REPLACE FUNCTION VARCHAR_TO_INTEGER(str in VARCHAR) RETURNS INTEGER LANGUAGE plpgsql AS $func$
  BEGIN
   RETURN str::INTEGER;
   EXCEPTION
      WHEN OTHERS THEN
        RAISE EXCEPTION 'ERROR: argument is not a number: %', str;
  END;
$func$;

--
do $$
BEGIN
  RAISE NOTICE '%', VARCHAR_TO_INTEGER('33');
END
$$;
