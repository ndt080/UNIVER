/*
Лабораторная работа 2
Петров Андрей Александрович
13 группа (кафедра ТП)
*/


ALTER TABLE "CurrentExpenditure" DROP CONSTRAINT "CurrExpID_ExpItemsID_foreign";
ALTER TABLE "CurrentIncome" DROP CONSTRAINT "CurrIncID_IncSourceID_foreign";
ALTER TABLE "Transaction" DROP CONSTRAINT "Trans_IDRecip_RecipID_foreign";
ALTER TABLE "Transaction" DROP CONSTRAINT "Trans_IDFMbr_FMbrID_foreign";
ALTER TABLE "Transaction" DROP CONSTRAINT "Trans_IDiSrc_ISrcID_foreign";
ALTER TABLE "Transaction" DROP CONSTRAINT "Trans_IDexpI_IDexpI_foreign";
ALTER TABLE "Transaction" DROP CONSTRAINT "Trans_IDTrSt_TrStID_foreign";
DROP TABLE "ExpenditureItems";
DROP TABLE "FamilyMember";
DROP TABLE "IncomeSource";
DROP TABLE "Recipient";
DROP TABLE "TransactionState";
DROP TABLE "CurrentExpenditure";
DROP TABLE "CurrentIncome";
DROP TABLE "Transaction";

CREATE TABLE "ExpenditureItems" (
  "ID" NUMBER NOT NULL,
  "Name" CHAR NOT NULL,
  "Description" CHAR DEFAULT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "FamilyMember" (
  "ID" NUMBER NOT NULL,
  "LastName" CHAR NOT NULL,
  "Name" CHAR NOT NULL,
  "DateBirthday" DATE NOT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "IncomeSource" (
  "ID" NUMBER NOT NULL,
  "Name" CHAR NOT NULL,
  "Description" CHAR DEFAULT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "Recipient" (
  "ID" NUMBER NOT NULL,
  "Last_name" CHAR NOT NULL,
  "First_name" CHAR NOT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "TransactionState" (
  "ID" NUMBER NOT NULL,
  "Name" CHAR(15) DEFAULT 'PAID' NOT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "CurrentExpenditure" (
  "ID" NUMBER NOT NULL,
  "ID_expenditureItems" NUMBER NOT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "CurrExpID_ExpItemsID_foreign" FOREIGN KEY ("ID_expenditureItems") REFERENCES "ExpenditureItems" ("ID") ON DELETE CASCADE
);

CREATE TABLE "CurrentIncome" (
  "ID" NUMBER NOT NULL,
  "ID_incomeSource" NUMBER NOT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "CurrIncID_IncSourceID_foreign" FOREIGN KEY ("ID_incomeSource") REFERENCES "IncomeSource" ("ID") ON DELETE CASCADE
);

CREATE TABLE "Transaction" (
  "ID" NUMBER NOT NULL,
  "ID_familyMember" NUMBER NOT NULL,
  "Date" DATE DEFAULT SYSDATE,
  "Amount" NUMBER CHECK ("Amount" > 0),
  "ID_incomeSource" NUMBER DEFAULT NULL,
  "ID_expenditureItems" NUMBER DEFAULT NULL,
  "ID_recipient" NUMBER NOT NULL,
  "ID_transactionState" NUMBER NOT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "Trans_IDRecip_RecipID_foreign" FOREIGN KEY ("ID_recipient") REFERENCES "Recipient" ("ID") ON DELETE
  SET
    NULL,
    CONSTRAINT "Trans_IDFMbr_FMbrID_foreign" FOREIGN KEY ("ID_familyMember") REFERENCES "FamilyMember" ("ID") ON DELETE
  SET
    NULL,
    CONSTRAINT "Trans_IDiSrc_ISrcID_foreign" FOREIGN KEY ("ID_incomeSource") REFERENCES "CurrentIncome" ("ID") ON DELETE CASCADE,
    CONSTRAINT "Trans_IDexpI_IDexpI_foreign" FOREIGN KEY ("ID_expenditureItems") REFERENCES "CurrentExpenditure" ("ID") ON DELETE CASCADE,
    CONSTRAINT "Trans_IDTrSt_TrStID_foreign" FOREIGN KEY ("ID_transactionState") REFERENCES "TransactionState" ("ID") ON DELETE
  SET
    NULL
);

 CREATE OR REPLACE TRIGGER "TRIGGER_check_dates"
   BEFORE INSERT OR UPDATE ON "Transaction"
   FOR EACH ROW
 BEGIN
   IF( :new."Date" < SYSDATE )
  THEN
    RAISE_APPLICATION_ERROR( -20001, "Invalid Date: Date must be >= current date");
  END IF;
 END;