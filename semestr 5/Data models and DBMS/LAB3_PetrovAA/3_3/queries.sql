/*
 Лабораторная работа №3
 Петров Андрей Александрович
 13 группа 3 курс (ТП)
 */

-- ЧАСТЬ 3_3
-- 1. Требуется представить имя каждого сотрудника таблицы emp, а также имя его руководителя.
SELECT CONCAT(EMPLOYEE.empname, ' works for ', MANAGER.empname) AS EMP_MANAGER
FROM emp EMPLOYEE
       JOIN emp MANAGER ON EMPLOYEE.MANAGER_ID = MANAGER.empno
ORDER BY EMPLOYEE.MANAGER_ID;

-- 2. Требуется представить имя каждого сотрудника таблицы emp (даже сотрудника, которому не назначен руководитель) и имя его руководителя.
SELECT CONCAT(empname, ' reposts to ', PRIOR empname) AS EMP_MANAGER
FROM emp START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR empno = MANAGER_ID;

-- 3. Требуется показать иерархию от CLARK до JOHN KLINTON.
SELECT LTRIM(SYS_CONNECT_BY_PATH(empname, ' -> '), ' -> ') AS EMPLOYEE_TREE
FROM emp
WHERE empname = 'JOHN KLINTON' START WITH empname = 'CLARK'
CONNECT BY empno = PRIOR MANAGER_ID;

-- 4. Требуется получить результирующее множество, описывающее иерархию всей таблицы.
SELECT (LTRIM(SYS_CONNECT_BY_PATH(empname, ' -> '), ' -> ')) AS TREE
FROM emp START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR empno = MANAGER_ID;

-- 5. Требуется показать уровень иерархии каждого сотрудника.
SELECT rpad('*', LEVEL, '*') || empname AS TREE
FROM emp START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR empno = MANAGER_ID
ORDER BY LEVEL;

-- 6. Требуется найти всех служащих, которые явно или неявно подчиняются ALLEN.
SELECT empname
FROM emp START WITH empname = 'ALLEN'
CONNECT BY PRIOR empno = MANAGER_ID;