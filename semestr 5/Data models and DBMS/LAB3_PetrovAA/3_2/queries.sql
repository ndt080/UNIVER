/*
 Лабораторная работа №3
 Петров Андрей Александрович
 13 группа 3 курс (ТП)
 */

-- ЧАСТЬ 3_2
-- 1. Найти имена сотрудников, получивших за годы начисления зарплаты минимальную зарплату.
SELECT emp.empname
FROM (salary
       INNER JOIN emp ON salary.empno = emp.empno)
WHERE salary.salvalue = (SELECT MIN(salvalue) FROM salary);

-- 2. Найти имена сотрудников, работавших или работающих в тех же отделах, в которых работал или работает сотрудник с именем RICHARD MARTIN.
SELECT DISTINCT empname
FROM (SELECT deptno
      FROM career,
           emp
      WHERE career.empno = emp.empno
        AND emp.empname = 'RICHARD MARTIN') as employee,
     emp,
     career
WHERE career.deptno = employee.deptno
  AND emp.empno = career.empno
  AND NOT empname = 'RICHARD MARTIN';

-- 3. Найти имена сотрудников, работавших или работающих в тех же отделах и должностях, что и сотрудник RICHARD MARTIN
SELECT empname
FROM (SELECT deptno, jobno
      FROM career,
           emp
      WHERE career.empno = emp.empno
        AND emp.empname = 'RICHARD MARTIN') as employee,
     career,
     emp
WHERE career.deptno = employee.deptno
  AND career.jobno = employee.jobno
  AND emp.empno = career.empno
  AND NOT empname = 'RICHARD MARTIN';

-- 4. Найти сведения о номерах сотрудников, получивших за какой-либо месяц зарплату большую, чем средняя зарплата за 2007 г. или большую чем средняя зарплата за 2008г.
SELECT DISTINCT empno
FROM salary
WHERE (salvalue) > ANY (SELECT AVG(salvalue) FROM salary WHERE year = 2007)
   OR (salvalue) > ANY (SELECT AVG(salvalue) FROM salary WHERE year = 2008);

-- 5. Найти сведения о номерах сотрудников, получивших зарплату за какой-либо месяц большую, чем средние зарплаты за все годы начислений.
SELECT DISTINCT empno
FROM salary
WHERE (salvalue) > ANY (SELECT AVG(salvalue) FROM salary);

-- 6. Определить годы, в которые начисленная средняя зарплата была больше средней зарплаты за все годы начислений.
SELECT year
FROM salary
GROUP BY year
HAVING AVG(salvalue) > (SELECT AVG(salvalue) FROM salary)
ORDER BY year;

-- 7. Определить номера отделов, в которых работали или работают сотрудники, имеющие начисления зарплаты.
SELECT dept.deptno
FROM dept,
     (SELECT DISTINCT deptno
      FROM career
             NATURAL JOIN emp
             NATURAL JOIN salary
      WHERE salary.salvalue IS NOT NULL) as tmp_dept
WHERE dept.deptno = tmp_dept.deptno
ORDER BY deptno;

-- 8. Определить номера отделов, в которых работали или работают сотрудники, имеющие начисления зарплаты. ОПЕРАТОР EXISTS
SELECT deptno
FROM dept
WHERE EXISTS(SELECT salvalue
             FROM career
                    NATURAL JOIN emp
                    NATURAL JOIN salary
             WHERE dept.deptno = career.deptno)
ORDER BY deptno;

-- 9. Определить номера отделов, для сотрудников которых не начислялась зарплата.
SELECT deptno
FROM dept
WHERE NOT EXISTS(SELECT salvalue
                 FROM career
                        NATURAL JOIN emp
                        NATURAL JOIN salary
                 WHERE dept.deptno = career.deptno)
ORDER BY deptno;

-- 10. Вывести сведения о карьере сотрудников с указанием названий и адресов отделов вместо номеров отделов.
SELECT emp.empno, emp.empname, dept.deptname, dept.deptaddr
FROM emp
       NATURAL JOIN career
       NATURAL JOIN dept
ORDER BY emp.empname, career.startdate;

-- 11. Определить целую часть средних зарплат, по годам начисления.
SELECT year, ROUND(AVG(salvalue)) as avg_salary
FROM salary
GROUP BY year
ORDER BY year;


SELECT year, CAST(AVG(salvalue) AS INTEGER) as avg_salary
FROM salary
GROUP BY year
ORDER BY year;

-- 12. Разделите сотрудников на возрастные группы: A) возраст 20-30 лет; B) 31-40 лет; C) 41-50; D) 51-60 или возраст не определён.
SELECT empno,
       empname,
       CASE
         WHEN (SELECT date_part('year',age(birthdate::date))) BETWEEN 20 AND 30 THEN 'A'
         WHEN (SELECT date_part('year',age(birthdate::date))) BETWEEN 31 AND 40 THEN 'B'
         WHEN (SELECT date_part('year',age(birthdate::date))) BETWEEN 41 AND 50 THEN 'C'
         WHEN (SELECT date_part('year',age(birthdate::date))) BETWEEN 51 AND 60 THEN 'D'
         ELSE NULL END AS age_group
FROM emp;

-- 13. Перекодируйте номера отделов, добавив перед номером отдела буквы BI для номеров <=20, буквы LN для номеров >=30.
SELECT dept.deptno,
       CASE
         WHEN dept.deptno <= 20 THEN CONCAT('BI', CAST(dept.deptno AS VARCHAR))
         WHEN dept.deptno >= 30 THEN CONCAT('LN', CAST(dept.deptno AS VARCHAR)) END as encode_deptno,
       dept.deptname,
       dept.deptaddr
FROM dept;

-- 14. Выдать информацию о сотрудниках из таблицы emp, заменив отсутствие данного о дате рождения датой 01-01-1000
SELECT empno, empname, COALESCE(birthdate, TO_DATE('01-01-1000', 'dd-mm-yyyy')) AS birthdate
FROM emp;
