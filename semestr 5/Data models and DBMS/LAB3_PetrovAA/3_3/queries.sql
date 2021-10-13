/*
 Лабораторная работа №3
 Петров Андрей Александрович
 13 группа 3 курс (ТП)
 */


-- ЧАСТЬ 3_2
-- -- 1
-- -- Требуется представить имя каждого сотрудника таблицы emp, а также имя его руководителя.
-- SELECT (A.empname, B.empname) AS EMP_MANAGER
-- FROM emp A
--        JOIN emp B ON A.MANAGER_ID = B.empno
-- ORDER BY A.MANAGER_ID;
--
-- -- 2
-- -- Требуется представить имя каждого сотрудника таблицы emp (даже сотрудника, которому не назначен руководитель) и имя его руководителя.
-- SELECT (empname, PRIOR empname) AS EMP_MANAGER
-- FROM emp START WITH MANAGER_ID IS NULL
-- CONNECT BY PRIOR empno = MANAGER_ID;
--
-- -- 3
-- -- Требуется показать иерархию от CLARK до JOHN KLINTON.
-- SELECT LTRIM(SYS_CONNECT_BY_PATH(empname, ' -> '), ' -> ') AS EMP_TREE
-- FROM emp
-- WHERE empname = 'JOHN KLINTON'
-- START
-- WITH empname = 'CLARK'
-- CONNECT BY empno = PRIOR MANAGER_ID;
--
-- -- 4
-- -- Требуется получить результирующее множество, описывающее иерархию всей таблицы.
-- SELECT (LTRIM(SYS_CONNECT_BY_PATH(empname, ' -> '), ' -> ')) AS TREE
-- FROM emp START WITH MANAGER_ID IS NULL
-- CONNECT BY PRIOR empno = MANAGER_ID;
--
-- -- 5
-- -- Требуется показать уровень иерархии каждого сотрудника.
-- SELECT rpad('*', LEVEL, '*') || empname AS TREE
-- FROM emp START WITH MANAGER_ID IS NULL
-- CONNECT BY PRIOR empno = MANAGER_ID
-- ORDER BY LEVEL;
--
-- -- 6
-- -- Требуется найти всех служащих, которые явно или неявно подчиняются ALLEN.
-- SELECT empname
-- FROM emp START WITH empname = 'ALLEN'
-- CONNECT BY PRIOR empno = MANAGER_ID;
--
--
--
-- -- lab 3_4
-- -- 1
-- -- Требуется используя значения столбца START_DATE получить дату за десять дней до и после приема на работу,
-- -- полгода до и после приема на работу, год до и после приема на работу сотрудника JOHN KLINTON.
-- SELECT startdate,
--        enddate,
--        (startdate - 10),
--        (enddate + 10),
--        ADD_MONTHS(startdate, -6),
--        ADD_MONTHS(enddate, 6),
--        ADD_MONTHS(startdate, -12),
--        ADD_MONTHS(enddate, 12)
-- FROM career
-- WHERE empno = (SELECT empno FROM emp WHERE empname = 'JOHN KLINTON');
--
-- -- 2
-- -- Требуется найти разность между двумя датами и представить результат в днях.
-- -- Вычислите разницу в днях между датами приема на работу сотрудников JOHN MARTIN и ALEX BOUSH.
-- SELECT ((SELECT startdate FROM career WHERE empno = (SELECT empno FROM emp WHERE empname = 'ALEX BOUSH')) -
--         (SELECT startdate FROM career WHERE empno = (SELECT empno FROM emp WHERE empname = 'JOHN MARTIN')))
-- FROM DUAL;
--
-- -- 3
-- -- Требуется найти разность между двумя датами в месяцах и в годах.
-- SELECT MONTHS_BETWEEN(SYSDATE, TO_DATE('08-26-1999', 'MM-DD-YYYY')),
--        (MONTHS_BETWEEN(SYSDATE, TO_DATE('08-26-1999', 'MM-DD-YYYY')) / 12)
-- FROM DUAL;
--
-- -- 4
-- -- Требуется определить интервал времени в днях между двумя датами.
-- -- Для каждого сотрудника 20-го отделе найти сколько дней прошло между датой его приема на работу и датой приема на работу следующего сотрудника.
-- SELECT startdate, ((LEAD(startdate, 1) OVER (ORDER BY startdate)) - startdate) AS DAYS_BETWEEN
-- FROM career
-- WHERE deptno = '20';
--
-- -- 5
-- -- Требуется подсчитать количество дней в году по столбцу START_DATE.
-- SELECT EXTRACT(YEAR FROM startdate)                                    AS YEAR,
--        (ADD_MONTHS(TRUNC(startdate, 'y'), 12) - TRUNC(startdate, 'y')) AS DAYS
-- FROM career;
--
-- -- 6
-- -- Требуется разложить текущую дату на день, месяц, год, секунды, минуты, часы. Результаты вернуть в численном виде.
-- SELECT TO_CHAR(SYSDATE, 'DD.MM.YYYY HH24:MI:SS')       AS DATE_CHAR,
--        TO_NUMBER(TO_CHAR(SYSDATE, 'DDMMYYYYHH24MISS')) AS DATE_NUMBER
-- FROM DUAL;
--
-- -- 7
-- -- Требуется получить первый и последний дни текущего месяца.
-- SELECT TRUNC(LAST_DAY(SYSDATE) - 1, 'mm') AS FIRST_DAY, SYSDATE, LAST_DAY(SYSDATE) AS LAST_DAY
-- FROM DUAL;
--
-- -- 8
-- -- Требуется возвратить даты начала и конца каждого из четырех кварталов данного года.
-- SELECT LEVEL                                               AS QUARTER,
--        ADD_MONTHS(trunc(sysdate, 'YEAR'), (LEVEL - 1) * 3) AS QUARTER_START,
--        ADD_MONTHS(trunc(sysdate, 'YEAR'), LEVEL * 3) - 1   AS QUARTER_END
-- FROM DUAL CONNECT BY LEVEL <= 4;
--
-- -- 9
-- -- Требуется найти все даты года, соответствующие заданному дню недели. Сформируйте список понедельников текущего года.
-- SELECT *
-- FROM (SELECT (trunc(sysdate, 'YEAR') + LEVEL - 1) AS DAY FROM DUAL CONNECT BY LEVEL <= 366)
-- WHERE TO_CHAR(DAY, 'fmday') = 'monday';
--
-- -- 10
-- -- Требуется создать календарь на текущий месяц. Календарь должен иметь семь столбцов в ширину и пять строк вниз.
-- WITH X AS (SELECT *
--            FROM (SELECT TRUNC(SYSDATE, 'MM') + LEVEL - 1                          MONTH_DATE,
--                         TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'IW')           WEEK_NUMBER,
--                         TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'DD')           DAY_NUMBER,
--                         TO_NUMBER(TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'D')) WEEK_DAY,
--                         TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'MM')           CURR_MONTH,
--                         TO_CHAR(SYSDATE, 'MM')                                    MONTH
--                  FROM DUAL CONNECT BY LEVEL <=31)
--            WHERE CURR_MONTH = MONTH)
--
-- SELECT MAX(CASE WEEK_DAY WHEN 2 THEN DAY_NUMBER END) AS MONDAY,
--        MAX(CASE WEEK_DAY WHEN 3 THEN DAY_NUMBER END) AS TUESDAY,
--        MAX(CASE WEEK_DAY WHEN 4 THEN DAY_NUMBER END) AS WEDNESDAY,
--        MAX(CASE WEEK_DAY WHEN 5 THEN DAY_NUMBER END) AS THURSDAY,
--        MAX(CASE WEEK_DAY WHEN 6 THEN DAY_NUMBER END) AS FRIDAY,
--        MAX(CASE WEEK_DAY WHEN 7 THEN DAY_NUMBER END) AS SATURDAY,
--        MAX(CASE WEEK_DAY WHEN 1 THEN DAY_NUMBER END) AS SUNDAY
-- FROM X
-- GROUP BY WEEK_NUMBER
-- ORDER BY WEEK_NUMBER;