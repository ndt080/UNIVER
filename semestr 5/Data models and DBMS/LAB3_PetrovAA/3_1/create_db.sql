DROP TABLE CAREER;
DROP TABLE SALARY;
DROP TABLE EMP;
DROP TABLE DEPT;
DROP TABLE JOB;

CREATE TABLE DEPT
(
  DEPTNO   NUMERIC(4, 0) PRIMARY KEY,

  DEPTNAME VARCHAR(50) NOT NULL,
  DEPTADDR VARCHAR(50)
);

COMMIT;

INSERT INTO DEPT
VALUES (10, 'ACCOUNTING', 'NEW YORK');
INSERT INTO DEPT
VALUES (20, 'RESEARCH', 'DALLAS');
INSERT INTO DEPT
VALUES (30, 'SALES', 'CHICAGO');
INSERT INTO DEPT
VALUES (40, 'OPERATIONS', 'BOSTON');

COMMIT;

CREATE TABLE EMP
(
  EMPNO      NUMERIC(4, 0) PRIMARY KEY,
  EMPNAME    VARCHAR(30) NOT NULL,
  BIRTHDATE  DATE,
  MANAGER_ID NUMERIC(4, 0) REFERENCES EMP (EMPNO)
);

INSERT INTO EMP
VALUES (7790, 'JOHN KLINTON', TO_DATE('9-07-1980', 'dd-mm-yyyy'), NULL);

INSERT INTO EMP
VALUES (7499, 'ALLEN', TO_DATE('20-2-1961', 'dd-mm-yyyy'), 7790);
INSERT INTO EMP
VALUES (7521, 'WARD', TO_DATE('22-2-1958', 'dd-mm-yyyy'), 7790);
INSERT INTO EMP
VALUES (7566, 'JONES', TO_DATE('2-4-1973', 'dd-mm-yyyy'), 7790);
INSERT INTO EMP
VALUES (7789, 'ALEX BOUSH', TO_DATE('21-09-1982', 'dd-mm-yyyy'), 7790);
INSERT INTO EMP
VALUES (7369, 'SMITH', TO_DATE('17-12-1948', 'dd-mm-yyyy'), 7789);
INSERT INTO EMP
VALUES (7654, 'JOHN MARTIN', TO_DATE('28-9-1945', 'dd-mm-yyyy'), 7789);
INSERT INTO EMP
VALUES (7698, 'RICHARD MARTIN', TO_DATE('1-5-1981', 'dd-mm-yyyy'), 7789);
INSERT INTO EMP
VALUES (7782, 'CLARK', NULL, 7499);
INSERT INTO EMP
VALUES (7788, 'SCOTT', TO_DATE('13-08-1987', 'dd-mm-yyyy'), 7499);

COMMIT;


CREATE TABLE JOB
(
  JOBNO     NUMERIC(4, 0) PRIMARY KEY,
  JOBNAME   VARCHAR(30) NOT NULL,
  MINSALARY NUMERIC(6, 0)
)
;

COMMIT;

INSERT INTO JOB
VALUES (1000, 'MANAGER', 2500);
INSERT INTO JOB
VALUES (1001, 'FINANCIAL DIRECTOR', 7500);
INSERT INTO JOB
VALUES (1002, 'EXECUTIVE DIRECTOR', 8000);
INSERT INTO JOB
VALUES (1003, 'SALESMAN', 1500);
INSERT INTO JOB
VALUES (1004, 'CLERK', 500);
INSERT INTO JOB
VALUES (1005, 'DRIVER', 1800);
INSERT INTO JOB
VALUES (1006, 'PRESIDENT', 15000);

COMMIT;


CREATE TABLE CAREER
(
  JOBNO     NUMERIC(4, 0) REFERENCES JOB (JOBNO) NOT NULL,
  EMPNO     NUMERIC(4, 0) REFERENCES EMP (EMPNO) NOT NULL,
  DEPTNO    NUMERIC(4, 0) REFERENCES DEPT (DEPTNO),
  STARTDATE DATE                                 NOT NULL,
  ENDDATE   DATE
)
;

COMMIT;

INSERT INTO CAREER
VALUES (1004, 7698, 10, TO_DATE('21-5-1999', 'dd-mm-yyyy'), TO_DATE('1-6-1999', 'dd-mm-yyyy'));
INSERT INTO CAREER
VALUES (1003, 7698, 10, TO_DATE('1-6-2010', 'dd-mm-yyyy'), NULL);

INSERT INTO CAREER
VALUES (1003, 7369, 20, TO_DATE('21-5-2005', 'dd-mm-yyyy'), NULL);
INSERT INTO CAREER
VALUES (1001, 7499, 30, TO_DATE('2-1-2003', 'dd-mm-yyyy'), TO_DATE('31-12-2005', 'dd-mm-yyyy'));
INSERT INTO CAREER
VALUES (1004, 7654, 20, TO_DATE('21-7-1999', 'dd-mm-yyyy'), TO_DATE('1-6-2004', 'dd-mm-yyyy'));
INSERT INTO CAREER
VALUES (1002, 7499, 30, TO_DATE('1-6-2006', 'dd-mm-yyyy'), TO_DATE('25-10-2008', 'dd-mm-yyyy'));
INSERT INTO CAREER
VALUES (1001, 7499, NULL, TO_DATE('12-10-2006', 'dd-mm-yyyy'), NULL);
INSERT INTO CAREER
VALUES (1004, 7369, 30, TO_DATE('1-7-2000', 'dd-mm-yyyy'), NULL);
INSERT INTO CAREER
VALUES (1001, 7499, 10, TO_DATE('1-1-2008', 'dd-mm-yyyy'), NULL);
INSERT INTO CAREER
VALUES (1005, 7789, 40, TO_DATE('1-1-2001', 'dd-mm-yyyy'), NULL);
INSERT INTO CAREER
VALUES (1006, 7790, 40, TO_DATE('1-10-2001', 'dd-mm-yyyy'), NULL);

COMMIT;

CREATE TABLE SALARY
(
  EMPNO    NUMERIC(4, 0) REFERENCES EMP (EMPNO),
  MONTH    NUMERIC(2, 0) CHECK (MONTH > 0 AND MONTH < 13),
  YEAR     NUMERIC(4, 0) CHECK (YEAR > 1987 AND YEAR < 2017),
  SALVALUE NUMERIC(6, 0)
);

COMMIT;

INSERT INTO SALARY
VALUES (7369, 05, 2007, 2580);
INSERT INTO SALARY
VALUES (7369, 06, 2007, 2650);
INSERT INTO SALARY
VALUES (7369, 07, 2007, 2510);
INSERT INTO SALARY
VALUES (7369, 08, 2007, 2495);
INSERT INTO SALARY
VALUES (7369, 09, 2007, 1750);
INSERT INTO SALARY
VALUES (7369, 10, 2007, 3540);
INSERT INTO SALARY
VALUES (7369, 11, 2007, 2580);
INSERT INTO SALARY
VALUES (7369, 12, 2007, 2050);
INSERT INTO SALARY
VALUES (7789, 01, 2008, 1850);
INSERT INTO SALARY
VALUES (7789, 02, 2008, 1900);
INSERT INTO SALARY
VALUES (7789, 03, 2008, 1950);
INSERT INTO SALARY
VALUES (7789, 04, 2008, 1950);
INSERT INTO SALARY
VALUES (7790, 05, 2009, 600);
INSERT INTO SALARY
VALUES (7790, 06, 2009, 650);
INSERT INTO SALARY
VALUES (7790, 07, 2009, 700);
INSERT INTO SALARY
VALUES (7499, 08, 2010, 8050);
INSERT INTO SALARY
VALUES (7499, 09, 2010, 8050);
INSERT INTO SALARY
VALUES (7499, 10, 2010, 8150);
INSERT INTO SALARY
VALUES (7369, 01, 2015, 3000);
INSERT INTO SALARY
VALUES (7369, 02, 2015, 3000);
INSERT INTO SALARY
VALUES (7369, 03, 2015, 3000);
INSERT INTO SALARY
VALUES (7369, 04, 2015, 3000);
INSERT INTO SALARY
VALUES (7369, 05, 2015, 3000);
INSERT INTO SALARY
VALUES (7499, 01, 2015, 3200);
INSERT INTO SALARY
VALUES (7499, 02, 2015, 3200);
INSERT INTO SALARY
VALUES (7499, 03, 2015, 3200);
INSERT INTO SALARY
VALUES (7499, 04, 2015, 3200);
INSERT INTO SALARY
VALUES (7499, 05, 2015, 3200);
INSERT INTO SALARY
VALUES (7499, 01, 2016, 3500);
INSERT INTO SALARY
VALUES (7499, 02, 2016, 3500);
INSERT INTO SALARY
VALUES (7499, 03, 2016, 3500);
INSERT INTO SALARY
VALUES (7499, 04, 2016, 3500);
INSERT INTO SALARY
VALUES (7369, 01, 2016, 3100);
INSERT INTO SALARY
VALUES (7369, 02, 2016, 3100);
INSERT INTO SALARY
VALUES (7369, 03, 2016, 3100);


COMMIT;