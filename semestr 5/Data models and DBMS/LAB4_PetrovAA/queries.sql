/*
 лабораторная работа №4
 общая часть
 Студент группы 11
 Еркович Е. Д.
*/
--1. Поднимите нижнюю границу минимальной заработной платы в таблице job до 1000$.
SELECT *
FROM job;

UPDATE job
SET minsalary = 1000
WHERE minsalary < 1000;

SELECT * FROM job;

--2. Поднимите минимальную зарплату в таблице job на 10% для всех специальностей, кроме финансового директора.
UPDATE job
SET minsalary = 1.10 * minsalary
WHERE jobname NOT IN ('FINANCIAL DIRECTOR');

--3. Поднимите минимальную зарплату в таблице job на 10% для клерков и на 20% для финансового директора (одним оператором).
UPDATE job
SET minsalary = (CASE jobname
                   WHEN 'CLERK' THEN 1.10 * minsalary
                   WHEN 'FINANCIAL DIRECTOR' THEN 1.20 * minsalary
                   ELSE minsalary END);

--4. Установите минимальную зарплату финансового директора равной 90% от зарплаты исполнительного директора.
UPDATE job
SET minsalary = 0.90 * (SELECT minsalary FROM job WHERE jobname = 'EXECUTIVE DIRECTOR')
WHERE jobname = 'FINANCIAL DIRECTOR';

--5. Приведите в таблице emp имена служащих, начинающиеся на букву ‘J’, к нижнему регистру.
UPDATE emp
SET EMPNAME = LOWER(EMPNAME)
WHERE EMPNAME LIKE 'J%';

SELECT * FROM emp;

--6. Измените в таблице emp имена служащих, состоящие из двух слов, так, чтобы оба слова в имени начинались с заглавной буквы, а продолжались прописными.
UPDATE emp
SET EMPNAME = INITCAP(EMPNAME)
WHERE EMPNAME LIKE ('% %');

--7. Приведите в таблице emp имена служащих к верхнему регистру.
UPDATE emp
SET EMPNAME = UPPER(EMPNAME);

--8. Перенесите отдел исследований (RESEARCH) в тот же город, в котором расположен отдел продаж (SALES).
UPDATE dept
SET deptaddr = (SELECT deptaddr FROM dept WHERE deptname = 'SALES')
WHERE deptname = 'RESEARCH';

SELECT * FROM dept;
--9. Добавьте нового сотрудника в таблицу emp. Его имя и фамилия должны совпадать с Вашими, записанными латинскими буквами согласно паспорту,
-- дата рождения также совпадает с Вашей.
SELECT *
FROM emp;
INSERT INTO emp (empno, EMPNAME, BIRTHDATE)
VALUES (7979, 'ANDREI PIATROU', TO_DATE('08-07-1999', 'dd-mm-yyyy'));

--10. Определите нового сотрудника (см. предыдущее задание) на работу в бухгалтерию (отдел ACCOUNTING) начиная с текущей даты.
INSERT INTO career
VALUES (1004, 7979, 10, CURRENT_DATE, NULL);
SELECT *
FROM career;

--11. Удалите все записи из таблицы TMP_EMP. Добавьте в нее информацию о сотрудниках, которые работают клерками в настоящий момент.
CREATE TABLE TMP_EMP AS SELECT * FROM emp;
DELETE FROM TMP_EMP;
SELECT * FROM TMP_EMP;

INSERT INTO TMP_EMP
SELECT *
FROM emp E
WHERE E.empno IN (SELECT empno FROM career
                         NATURAL JOIN job
                         WHERE jobname = 'CLERK' AND startdate IS NOT NULL AND enddate IS NULL);

--12. Добавьте в таблицу TMP_EMP информацию о тех сотрудниках, которые уже не работают на предприятии,
-- а в период работы занимали только одну должность.
INSERT INTO TMP_EMP
SELECT * FROM emp
WHERE empno IN (SELECT E.empno FROM emp E
                       JOIN career C ON E.empno = C.empno
                WHERE enddate IS NOT NULL
                GROUP BY E.empno
                HAVING COUNT(C.JOBNO) = 1);

SELECT * FROM TMP_EMP;

--13. Выполните тот же запрос для тех сотрудников, которые никогда не приступали к работе на предприятии.
INSERT INTO TMP_EMP
SELECT *
FROM emp E
WHERE E.empno IN (SELECT empno FROM career WHERE startdate IS NULL);

--14. Удалите все записи из таблицы TMP_JOB и добавьте в нее информацию по тем специальностям,
-- которые не используются в настоящий момент на предприятии.
CREATE TABLE TMP_JOB AS SELECT * FROM job;
DELETE FROM TMP_JOB;

INSERT INTO TMP_JOB
SELECT * FROM job J WHERE NOT EXISTS(SELECT * FROM career C WHERE C.jobno = J.JOBNO);

SELECT * FROM TMP_JOB;
--15. Начислите зарплату в размере 120% минимального должностного оклада всем сотрудникам, работающим на предприятии.
-- Зарплату начислять по должности, занимаемой сотрудником в настоящий момент и
-- отнести ее на прошлый месяц относительно текущей даты.
INSERT INTO salary (empno, salvalue, MONTH, YEAR)
SELECT empno,
       1.20 * minsalary,
       EXTRACT(MONTH FROM ADD_MONTHS(CURRENT_DATE, -1)),
       EXTRACT(YEAR FROM ADD_MONTHS(CURRENT_DATE, -1))
FROM career
       NATURAL JOIN job WHERE startdate IS NOT NULL AND enddate IS NULL;

--16. Удалите данные о зарплате за прошлый год.
DELETE
FROM salary
WHERE YEAR = EXTRACT(YEAR FROM CURRENT_DATE) - 1;

SELECT * FROM salary;
--17. Удалите информацию о карьере сотрудников, которые в настоящий момент уже не работают на предприятии, но когда-то работали.
DELETE
FROM career
WHERE enddate IS NOT NULL AND enddate < CURRENT_DATE;

SELECT * FROM career;
--18. Удалите информацию о начисленной зарплате сотрудников, которые в настоящий момент уже не работают на предприятии
-- (можно использовать результаты работы предыдущего запроса)
DELETE
FROM salary
WHERE empno = (SELECT empno FROM career WHERE enddate IS NOT NULL AND enddate < CURRENT_DATE);

SELECT * FROM salary;
--19. Удалите записи из таблицы emp для тех сотрудников, которые никогда не приступали к работе на предприятии.
DELETE
FROM emp
WHERE empno = (SELECT empno FROM career WHERE startdate IS NULL);

SELECT * FROM emp;