CREATE OR REPLACE PACKAGE HOME_FINANCE
AS
    PROCEDURE EXP_BETWEEN(START IN DATE, END IN DATE, RESULT IN OUT SYS_REFCURSOR);
    FUNCTION GET_RESIDUE(NAME IN VARCHAR2) RETURN NUMBER;
END HOME_FINANCE;
/

--1
PROCEDURE EXP_BETWEEN (START_ IN DATE, END_ IN DATE, RESULT IN OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN RESULT FOR WITH NAME_TYPE AS (
        SELECT FM."ID"                           AS FM_ID,
               FM."LastName" || ' ' || FM."Name" AS NAME,
               EX."ID"                           AS EXPENSE_ID,
               EX."Name"
        FROM "FamilyMember" FM
                 CROSS JOIN "CurrentExpenditure" EX),
                         TRNS AS (SELECT FAMILY_MEMBER_ID AS FM_ID,
                                         "ID_expenditureItems",
                                         SUM("Amount")    AS AMOUNT
                                  FROM "Transaction"
                                  WHERE "Date" BETWEEN START_ AND END_
                                    AND "ID_expenditureItems" IS NOT NULL
                                  GROUP BY ("ID_familyMember", "ID_expenditureItems")
                         )
                    SELECT NT."Name"        AS NAME_,
                           NVL(T.AMOUNT, 0) AS AMOUNT
                    FROM NAME_TYPE NT
                             LEFT JOIN TRNS T
                                       ON (T.FM_ID = NT.FM_ID AND
                                           T.EXPENSE_ID = NT.EXPENSE_ID)
                    ORDER BY NAME_;
END EXP_BETWEEN;

--2
FUNCTION GET_RESIDUE(NAME IN VARCHAR2)
    RETURN NUMBER
IS
    RES NUMBER(10,2);
BEGIN
    SELECT NVL(SUM((CASE
                        WHEN T."ID_incomeSource" IS NULL
                            THEN -T."Amount"
                        ELSE T."Amount" END)), 0)
    INTO RES
    FROM "Transaction" T
             JOIN "FamilyMember" FM
                  ON (T."ID_familyMember" = FM."ID")
    WHERE FM."LastName" || ' ' || FM."Name" = NAME;
    RETURN RES;
END;

END HOME_FINANCE;
/



--3
CREATE OR REPLACE TRIGGER CHECK_MINOR_EXP
    BEFORE INSERT OR UPDATE
    ON "Transaction"
    FOR EACH ROW
DECLARE
    D DATE;
BEGIN
    IF ((:NEW."ID_expenditureItems" IS NOT NULL) AND (:NEW."ID_incomeSource" IS NULL))
        AND (:NEW."Amount" > 50)
        AND (INSERTING OR UPDATING ('Date') OR UPDATING ('Amount')
            OR UPDATING ('ID_incomeSource') OR UPDATING ('ID_expenditureItems')
            OR UPDATING ('ID_familyMember'))
    THEN
        SELECT "DateBirthday"
        INTO D
        FROM "FamilyMember"
        WHERE "ID" = :NEW."ID_familyMember";

        IF FLOOR(MONTHS_BETWEEN(:NEW."Date", D) / 12) < 18
        THEN
            RAISE_APPLICATION_ERROR(-20011, 'EXPENSES OF A MINOR SHOULD NOT
                                EXCEED 50 RUBLES.');
        END IF;
    END IF;
END;
/
