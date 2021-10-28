/*
 Лабораторная работа №3
 Петров Андрей Александрович
 13 группа 3 курс (ТП)
 */

-- ЧАСТЬ 3_4
-- 1. Требуется используя значения столбца START_DATE получить дату за десять дней до и после приема на работу, полгода до и после приема на работу, год до и после приема на работу сотрудника JOHN KLINTON.
SELECT startdate,
       enddate,
       (startdate - 10)                       AS startdate_M10D,
       (enddate + 10)                         AS enddate_P10D,
       DATE(startdate - interval '6 months')  AS startdate_M6M,
       DATE(enddate + interval '6 months')    AS enddate_P6M,
       DATE(startdate - interval '12 months') AS startdate_M12M,
       DATE(enddate + interval '12 months')   AS enddate_P12M
FROM career
WHERE empno = (SELECT empno FROM emp WHERE empname = 'JOHN KLINTON');

-- 2. Требуется найти разность между двумя датами и представить результат в днях. Вычислите разницу в днях между датами приема на работу сотрудников JOHN MARTIN и ALEX BOUSH.
SELECT CONCAT(empA.startdate - empB.startdate, ' days') as difference
FROM (SELECT startdate FROM career WHERE empno = (SELECT empno FROM emp WHERE empname = 'ALEX BOUSH')) AS empA,
     (SELECT startdate FROM career WHERE empno = (SELECT empno FROM emp WHERE empname = 'JOHN MARTIN')) AS empB;

-- 3. Требуется найти разность между двумя датами в месяцах и в годах.
SELECT DATE_PART('year', age(now(), '1999-07-08'))                                                     as YEARS,
       DATE_PART('month', age(now(), '1999-07-08')) + DATE_PART('year', age(now(), '1999-07-08')) * 12 AS MONTHS;

-- 4. Требуется определить интервал времени в днях между двумя датами. Для каждого сотрудника 20-го отделе найти сколько дней прошло между датой его приема на работу и датой приема на работу следующего сотрудника.
SELECT startdate, ((LEAD(startdate, 1) OVER (ORDER BY startdate)) - startdate) AS DAYS_BETWEEN
FROM career
WHERE deptno = '20';

-- 5. Требуется подсчитать количество дней в году по столбцу START_DATE.
SELECT EXTRACT(YEAR FROM startdate)                                                         AS YEAR,
       date_trunc('YEAR', startdate) + interval '12 months' - date_trunc('YEAR', startdate) AS DAYS
FROM career;

-- 6. Требуется разложить текущую дату на день, месяц, год, секунды, минуты, часы. Результаты вернуть в численном виде.
SELECT TO_CHAR(CURRENT_DATE, 'DD.MM.YYYY HH24:MI:SS')       AS DATE_CHAR,
       TO_NUMBER(TO_CHAR(CURRENT_DATE, 'DDMMYYYYHH24MISS')) AS DATE_NUMBER;

-- 7. Требуется получить первый и последний дни текущего месяца.
SELECT DATE(date_trunc('MONTH', now()) + INTERVAL '1 MONTH - 1 day') AS FIRST_DAY,
       CURRENT_DATE,
       DATE(date_trunc('MONTH', now()))                              AS LAST_DAY;

-- 8. Требуется возвратить даты начала и конца каждого из четырех кварталов данного года.
SELECT LEVEL                                               AS QUARTER,
       ADD_MONTHS(trunc(sysdate, 'YEAR'), (LEVEL - 1) * 3) AS QUARTER_START,
       ADD_MONTHS(trunc(sysdate, 'YEAR'), LEVEL * 3) - 1   AS QUARTER_END
FROM DUAL CONNECT BY LEVEL <= 4;

-- 9. Требуется найти все даты года, соответствующие заданному дню недели. Сформируйте список понедельников текущего года.
SELECT *
FROM (SELECT (trunc(sysdate, 'YEAR') + LEVEL - 1) AS DAY FROM DUAL CONNECT BY LEVEL <= 366)
WHERE TO_CHAR(DAY, 'fmday') = 'monday';

-- 10. Требуется создать календарь на текущий месяц. Календарь должен иметь семь столбцов в ширину и пять строк вниз.
WITH X AS (SELECT *
           FROM (SELECT TRUNC(SYSDATE, 'MM') + LEVEL - 1                          MONTH_DATE,
                        TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'IW')           WEEK_NUMBER,
                        TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'DD')           DAY_NUMBER,
                        TO_NUMBER(TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'D')) WEEK_DAY,
                        TO_CHAR(TRUNC(SYSDATE, 'MM') + LEVEL - 1, 'MM')           CURR_MONTH,
                        TO_CHAR(SYSDATE, 'MM')                                    MONTH
                 FROM DUAL CONNECT BY LEVEL <=31)
           WHERE CURR_MONTH = MONTH)

SELECT MAX(CASE WEEK_DAY WHEN 2 THEN DAY_NUMBER END) AS MONDAY,
       MAX(CASE WEEK_DAY WHEN 3 THEN DAY_NUMBER END) AS TUESDAY,
       MAX(CASE WEEK_DAY WHEN 4 THEN DAY_NUMBER END) AS WEDNESDAY,
       MAX(CASE WEEK_DAY WHEN 5 THEN DAY_NUMBER END) AS THURSDAY,
       MAX(CASE WEEK_DAY WHEN 6 THEN DAY_NUMBER END) AS FRIDAY,
       MAX(CASE WEEK_DAY WHEN 7 THEN DAY_NUMBER END) AS SATURDAY,
       MAX(CASE WEEK_DAY WHEN 1 THEN DAY_NUMBER END) AS SUNDAY
FROM X
GROUP BY WEEK_NUMBER
ORDER BY WEEK_NUMBER;