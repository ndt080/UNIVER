SELECT "LastName",
       "Name",
       (SELECT NVL(SUM("Amount"), 0)
        FROM "Transaction" A
        WHERE A."ID_familyMember" = FM."ID"
          AND "ID_incomeSource" IS NOT NULL
          AND "ID_expenditureItems" IS NULL
          AND A."Date" BETWEEN TO_DATE('01-APR-2021', 'DD-MON-YYYY') AND TO_DATE('22-NOV-2021 23:59:59', 'DD-MON-YYYY HH24:MI:SS')) AS INCOME,
       (SELECT NVL(SUM("Amount"), 0)
        FROM "Transaction" B
        WHERE B."ID_familyMember" = FM."ID"
          AND "ID_incomeSource" IS NULL
          AND "ID_expenditureItems" IS NOT NULL
          AND B."Date" BETWEEN TO_DATE('01-APR-2021', 'DD-MON-YYYY') AND TO_DATE('22-NOV-2021 23:59:59', 'DD-MON-YYYY HH24:MI:SS')) AS EXPENSE
FROM "FamilyMember" FM;


WITH Expenditure AS (SELECT C."ID". I."Name" FROM "CurrentExpenditure" C JOIN "ExpenditureItems" I ON C."ID_expenditureItems" = I."ID")

SELECT Expenditure."Name" AS EXPENSE, T.ID_recipient AS "RECIPIENT", NVL(SUM(T."Amount"), 0) AS SUM
FROM "Transaction" T JOIN Expenditure ON T."ID_expenditureItems" = Expenditure."ID"
GROUP BY ROLLUP (Expenditure."Name");



WITH Expenditure AS (SELECT C."ID". E."Name" FROM "CurrentExpenditure" C JOIN "ExpenditureItems" E ON C."ID_expenditureItems" = E."ID")
WITH Income AS (SELECT C."ID". I."Name" FROM "CurrentIncome" C JOIN "IncomeSource" I ON C."ID_incomeSource" = I."ID")

SELECT "Name", NVL(MIN("Amount"), 0) AS MIN, NVL(MAX("Amount"), 0) AS MAX FROM (
SELECT E."Name", P."Amount"
FROM Expenditure E LEFT JOIN "Transaction" P ON (E."ID" = P."ID_expenditureItems")
) GROUP BY "Name"
UNION
SELECT "Name", NVL(MIN("Amount"), 0) AS MIN, NVL(MAX("Amount"), 0) AS MAX FROM (
SELECT I."Name", P."Amount"
FROM Income I LEFT JOIN "Transaction" P ON (I."ID" = P."ID_incomeSource")
) GROUP BY "Name";