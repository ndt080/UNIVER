/*
 * КОНТРОЛЬНАЯ РАБОТА №1
 * ВАРИАНТ 16. ДОМАШНЯЯ БУХГАЛТЕРИЯ
 * Петров Андрей Александрович, 13 группа 3 курс
 */

CREATE TABLE "Currency"
(
    "ID"   NUMBER       NOT NULL,
    "Name" VARCHAR(255) NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "ExpenditureItems"
(
    "ID"          NUMBER       NOT NULL,
    "Name"        VARCHAR(128) NOT NULL,
    "Description" VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "FamilyMember"
(
    "ID"           NUMBER      NOT NULL,
    "LastName"     VARCHAR(24) NOT NULL,
    "Name"         VARCHAR(24) NOT NULL,
    "DateBirthday" DATE        NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "IncomeSource"
(
    "ID"          NUMBER       NOT NULL,
    "Name"        VARCHAR(128) NOT NULL,
    "Description" VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "TransactionState"
(
    "ID"   NUMBER                     NOT NULL,
    "Name" VARCHAR(24) DEFAULT 'PAID' NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "CurrentExpenditure"
(
    "ID"                  NUMBER NOT NULL,
    "ID_expenditureItems" NUMBER NOT NULL,
    PRIMARY KEY ("ID"),
    CONSTRAINT "CurrExpID_ExpItemsID_foreign"
        FOREIGN KEY ("ID_expenditureItems") REFERENCES "ExpenditureItems" ("ID")
            ON DELETE SET NULL
);

CREATE TABLE "CurrentIncome"
(
    "ID"              NUMBER NOT NULL,
    "ID_incomeSource" NUMBER NOT NULL,
    PRIMARY KEY ("ID"),
    CONSTRAINT "CurrIncID_IncSourceID_foreign"
        FOREIGN KEY ("ID_incomeSource") REFERENCES "IncomeSource" ("ID")
            ON DELETE SET NULL
);

CREATE TABLE "Transaction"
(
    "ID"                  NUMBER                 NOT NULL,
    "ID_familyMember"     NUMBER                 NOT NULL,
    "Date"                DATE   DEFAULT SYSDATE NOT NULL,
    "Amount"              NUMBER CHECK ("Amount" > 0),
    "ID_incomeSource"     NUMBER DEFAULT NULL,
    "ID_expenditureItems" NUMBER DEFAULT NULL,
    "ID_currency"         NUMBER                 NOT NULL,
    "ID_transactionState" NUMBER                 NOT NULL,
    PRIMARY KEY ("ID"),
    CONSTRAINT "Trans_IDCurr_CurrID_foreign"
        FOREIGN KEY ("ID_currency")
            REFERENCES "Currency" ("ID")
            ON DELETE SET NULL,
    CONSTRAINT "Trans_IDFMbr_FMbrID_foreign"
        FOREIGN KEY ("ID_familyMember")
            REFERENCES "FamilyMember" ("ID")
            ON DELETE SET NULL,
    CONSTRAINT "Trans_IDiSrc_ISrcID_foreign"
        FOREIGN KEY ("ID_incomeSource")
            REFERENCES "CurrentIncome" ("ID")
            ON DELETE CASCADE,
    CONSTRAINT "Trans_IDexpI_IDexpI_foreign"
        FOREIGN KEY ("ID_expenditureItems")
            REFERENCES "CurrentExpenditure" ("ID")
            ON DELETE CASCADE,
    CONSTRAINT "Trans_IDTrSt_TrStID_foreign"
        FOREIGN KEY ("ID_transactionState")
            REFERENCES "TransactionState" ("ID")
            ON DELETE SET NULL
);