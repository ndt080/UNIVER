/*
 Лабораторная работа №3
 Петров Андрей Александрович
 13 группа 3 курс (ТП)
 */

-- ЧАСТЬ 3_5
-- 1. Составьте на выборку данных с использованием рефлексивного соединения для таблицы из задания 5 лабораторной работы №2
SELECT T1."ID_familyMember", T1."Date", T2."ID_recipient"
FROM "Transaction" T1
         JOIN "Transaction" T2 ON T1."ID_familyMember" = T2."ID_familyMember" AND T1."ID_expenditureItems" IS NOT NULL;

-- 2. Составьте на выборку данных с использованием простого оператора CASE;
SELECT "ID_familyMember",
       CASE
           WHEN "ID_incomeSource" IS NOT NULL
               THEN "Income"
           ELSE "Expenditure"
           END AS "Type transaction",
       "Amount"
FROM "Transaction";

-- 3. Составьте на выборку данных с использованием поискового оператора CASE;
SELECT ID,
       "Date",
       CASE
           WHEN "Date" >= '2021-01-01' AND "Date" <= SYSDATE AND "ID_expenditureItems" IS NOT NULL
               THEN SUM("Amount") END AS "Expenditure"
FROM "Transaction";

-- 4. Составьте на выборку данных с использованием оператора WITH;
WITH SUM AS (SELECT "ID_familyMember", SUM("Amount") SP
             FROM "Transaction"
             WHERE "ID_expenditureItems" IS NOT NULL
             GROUP BY "ID_familyMember")
SELECT "ID_familyMember", "LastName", "Name"
FROM "FamilyMember"
         JOIN SUM ON "FamilyMember".ID = SUM."ID_familyMember";

-- 5. Составьте на выборку данных с использованием встроенного представления;
SELECT MEMBER."LastName", MEMBER."Name", "Transaction"."Date", "Transaction"."Amount"
FROM "Transaction"
         JOIN (SELECT "ID", "LastName", "Name" FROM "FamilyMember") MEMBER
              ON "Transaction"."ID_familyMember" = MEMBER."ID";

-- 6. Составьте на выборку данных с использованием некоррелированного запроса;                        //ДЛЯ ПРИМЕРА, ХЗ КАК У МЕНЯ СДЕЛАТЬ НОРМАЛЬНО
SELECT *
FROM Transaction
WHERE Amount > (SELECT AVG(Amount) FROM "Transaction" WHERE "ID_expenditureItems" IS NOT NULL);

-- 7. Составьте на выборку данных с использованием коррелированного запроса;
SELECT "Date",
       "Amount",
       (SELECT "LastName", "Name"
        FROM "FamilyMember"
        WHERE "FamilyMember".ID = "Transaction"."ID_familyMember") AS "FamilyMember"
FROM "Transaction";


-- 8. Составьте на выборку данных с использованием функции NULLIF;
SELECT "Name", "Description", NULLIF("Description", "Something income source")
FROM "IncomeSource";


-- 9. Составьте на выборку данных с использованием функции NVL2;
SELECT "ID_familyMember", "Date", "Amount", NVL2("ID_expenditureItems", "Expenditure", "Income") AS "TransactionType"
FROM "Transaction";

-- 10. Составьте на выборку данных с использованием TOP-N анализа;
SELECT "ID_familyMember", "Amount"
FROM (SELECT * FROM "Transaction" ORDER BY "Amount" DESC)
WHERE ROWNUM <= 10;

-- 11. Составьте на выборку данных с использованием функции ROLLUP.
SELECT "ID_familyMember", SUM(*)
FROM "Transaction"
WHERE "ID_expenditureItems" IS NOT NULL
GROUP BY ROLLUP ("ID_familyMember");

-- 12. Составьте запрос на использование оператора MERGE языка манипулирования данными.
CREATE TABLE ARC_ORDER
(
    "ID"          NUMBER        NOT NULL ENABLE,
    "ORDER_DATE"  DATE          NOT NULL ENABLE,
    "PRICE"       NUMBER        NOT NULL ENABLE,
    "CLIENT_NAME" NVARCHAR2(20) NOT NULL ENABLE,
    CONSTRAINT "ARC_ORDER_PK" PRIMARY KEY ("ID")
);

MERGE INTO ARC_ORDER AO
USING (
    SELECT "ORDER"."ID", ORDER_DATE, NVL2(DISCOUNT, PRICE, PRICE - DISCOUNT * PRICE) PRICE, "CLIENT"."NAME" CLIENT_NAME
    FROM "ORDER"
             JOIN "CLIENT" ON "ORDER"."CLIENT_ID" = "CLIENT"."ID"
    WHERE STATUS_ID IN (3, 4)) O
ON (AO.ID = O.ID)
WHEN MATCHED THEN
    UPDATE
    SET AO.CLIENT_NAME = O.CLIENT_NAME,
        AO.PRICE       = O.PRICE,
        AO.ORDER_DATE  = O.ORDER_DATE
WHEN NOT MATCHED THEN
    INSERT (AO.ID, AO.ORDER_DATE, AO.PRICE, AO.CLIENT_NAME)
    VALUES (O.ID, O.ORDER_DATE, O.PRICE, O.CLIENT_NAME);


CREATE TABLE "TestFamilyMember"
(
    "ID"           NUMBER NOT NULL,
    "LastName"     CHAR   NOT NULL,
    "Name"         CHAR   NOT NULL,
    "DateBirthday" DATE   NOT NULL,
    PRIMARY KEY ("ID")
);

MERGE "TestFamilyMember" AS T_Base --Целевая таблица
USING "FamilyMember" AS T_Source --Таблица источник
ON (T_Base."ID" = T_Source."ID") --Условие объединения
WHEN MATCHED THEN --Если истина (UPDATE)
        UPDATE
        SET "LastName"     = T_Source."LastName",
            "Name"         = T_Source."Name",
            "DateBirthday" = T_Source."DateBirthday"
WHEN NOT MATCHED THEN --Если НЕ истина (INSERT)
        INSERT ("ID", "LastName", "Name", "DateBirthday")
        VALUES (T_Source."ID", T_Source."LastName", T_Source."Name", T_Source."DateBirthday")
--Посмотрим, что мы сделали
OUTPUT $action AS [Операция], Inserted."ID",
                   Inserted."LastName" AS LastNameNEW,
                   Inserted."Name" AS NameNEW,
                   Inserted."DateBirthday" AS DateBirthdayNEW,
                   Deleted."LastName" AS LastNameOLD,
                   Deleted."Name" AS NameOLD;
                   Deleted."DateBirthday" AS DateBirthdayOLD;