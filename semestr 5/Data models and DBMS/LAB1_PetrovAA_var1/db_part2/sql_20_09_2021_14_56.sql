CREATE TABLE "ExpenditureItems" (
  "ID" NUMBER NOT NULL,
  "Name" CHAR NOT NULL,
  "Description" CHAR DEFAULT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "IncomeSource" (
  "ID" NUMBER NOT NULL,
  "Name" CHAR NOT NULL,
  "Description" CHAR DEFAULT NULL,
  PRIMARY KEY ("ID")
);

CREATE TABLE "CurrentExpenditure" (
  "ID" NUMBER NOT NULL,
  "ID_expenditureItems" NUMBER NOT NULL,
  "Date" DATE NOT NULL,
  "Amount" NUMBER NOT NULL,
  "ID_familyMember" NUMBER NOT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "CurrentExpenditure_ID_expenditureItems_ExpenditureItems_ID_foreign" FOREIGN KEY ("ID_expenditureItems") REFERENCES "ExpenditureItems" ("ID")
);

CREATE TABLE "CurrentIncome" (
  "ID" NUMBER NOT NULL,
  "ID_incomeSource" NUMBER NOT NULL,
  "Date" DATE NOT NULL,
  "Amount" NUMBER NOT NULL,
  "ID_familyMember" NUMBER NOT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "CurrentIncome_ID_incomeSource_IncomeSource_ID_foreign" FOREIGN KEY ("ID_incomeSource") REFERENCES "IncomeSource" ("ID")
);

CREATE TABLE "FamilyMember" (
  "ID" NUMBER NOT NULL,
  "LastName" CHAR NOT NULL,
  "Name" CHAR NOT NULL,
  "DateBirthday" DATE DEFAULT NULL,
  "ID_relationDegree" NUMBER NOT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "FamilyMember_ID_CurrentIncome_ID_familyMember_foreign" FOREIGN KEY ("ID") REFERENCES "CurrentIncome" ("ID_familyMember"),
  CONSTRAINT "FamilyMember_ID_CurrentExpenditure_ID_familyMember_foreign" FOREIGN KEY ("ID") REFERENCES "CurrentExpenditure" ("ID_familyMember")
);

CREATE TABLE "RelationDegree" (
  "ID" NUMBER NOT NULL,
  "Name" CHAR NOT NULL,
  "Description" NUMBER DEFAULT NULL,
  PRIMARY KEY ("ID"),
  CONSTRAINT "RelationDegree_ID_FamilyMember_ID_relationDegree_foreign" FOREIGN KEY ("ID") REFERENCES "FamilyMember" ("ID_relationDegree")
);