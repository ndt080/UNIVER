/*
 Лабораторная работа №3
 Петров Андрей Александрович
 13 группа 3 курс (ТП)
 */

-- ЧАСТЬ 3_1
-- 1. Выдать информацию о местоположении отдела продаж (SALES) компании.
SELECT deptaddr
FROM dept
WHERE deptname = 'SALES';

-- 2. Выдать информацию об отделах, расположенных в Chicago и New York
SELECT deptname, deptaddr
FROM dept
WHERE deptaddr = 'CHICAGO'
   OR deptaddr = 'NEW YORK';

-- 3. Найти минимальную заработную плату, начисленную в 2009 году.
SELECT MIN(salvalue)
FROM salary
WHERE year = 2009;

-- 4. Выдать информацию обо всех работниках, родившихся не позднее 1 января 1960 года.
SELECT empno, empname, birthdate, manager_id
FROM emp
WHERE birthdate <= TO_DATE('01-01-1960', 'dd-mm-yyyy');

-- 5. Подсчитать число работников, сведения о которых имеются в базе данных.
SELECT COUNT(*)
FROM emp
WHERE birthdate IS NOT NULL
  AND manager_id IS NOT NULL;

-- 6. Найти работников, чьё имя состоит из одного слова. Имена выдать на нижнем регистре, с удалением стоящей справа буквы t.
SELECT RTRIM(LOWER(empname), 't')
FROM emp
WHERE empname NOT LIKE '% %';

-- 7. Выдать информацию о работниках, указав дату рождения в формате день(число), месяц(название), год(название).
SELECT empno, empname, TO_CHAR(birthdate, 'DD MONTH YEAR') AS birthdate
FROM emp;

-- Тоже, но год числом.
SELECT empno, empname, TO_CHAR(birthdate, 'DD MONTH YYYY') AS birthdate
FROM emp;

-- 8. Выдать информацию о должностях, изменив названия должности “CLERK” и “DRIVER” на “WORKER”.
SELECT CASE WHEN jobname = 'CLERK' OR jobname = 'DRIVER' THEN 'WORKER' ELSE jobname END AS new_jobname
FROM JOB;

-- 9. Определите среднюю зарплату за годы, в которые были начисления не менее чем за три месяца.
SELECT year, AVG(salvalue)
FROM salary
GROUP BY year
HAVING COUNT(month) >= 3;

-- 10. Выведете ведомость получения зарплаты с указанием имен служащих.
SELECT emp.empname, salary.month, salary.salvalue
FROM emp,
     salary
WHERE emp.empno = salary.empno;

-- 11. Укажите сведения о начислении сотрудникам зарплаты, попадающей в вилку: минимальный оклад по должности - минимальный оклад по должности плюс пятьсот. Укажите соответствующую вилке должность.
SELECT emp.empname, job.jobname, salary.salvalue, job.minsalary
FROM salary
       INNER JOIN emp ON salary.empno = emp.empno
       INNER JOIN career ON career.empno = emp.empno
       INNER JOIN job ON job.jobno = career.jobno
WHERE salary.salvalue > job.minsalary
  AND salary.salvalue < job.minsalary + 500;

-- 12. Укажите сведения о заработной плате, совпадающей с минимальными окладами по должностям (с указанием этих должностей).
SELECT emp.empname, salary.salvalue, job.minsalary, job.jobname
FROM salary
       INNER JOIN emp ON (salary.empno = emp.empno)
       INNER JOIN career ON (emp.empno = career.empno)
       INNER JOIN job ON (career.jobno = job.jobno)
WHERE salary.salvalue = job.minsalary;

-- 13. Найдите сведения о карьере сотрудников с указанием вместо номера сотрудника его имени.
SELECT emp.empname, career.startdate, career.enddate
FROM emp
       NATURAL JOIN career;

-- 14. Найдите сведения о карьере сотрудников с указанием вместо номера сотрудника его имени.
SELECT emp.empname, career.startdate, career.enddate
FROM emp
       INNER JOIN career ON (emp.empno = career.empno);

-- 15. Выдайте сведения о карьере сотрудников с указанием их имён, наименования должности, и названия отдела.
SELECT emp.empname, dept.deptname, job.jobname, career.startdate, career.enddate
FROM emp
       NATURAL JOIN career
       NATURAL JOIN dept
       NATURAL JOIN job
ORDER BY emp.empname, career.startdate;

-- 16. Выдайте сведения о карьере сотрудников с указанием их имён
SELECT emp.empname, career.startdate, career.enddate
FROM emp
       RIGHT OUTER JOIN career ON (emp.empno = career.empno)
ORDER BY emp.empname, career.startdate;