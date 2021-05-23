DROP SCHEMA PUBLIC CASCADE;

create table BANK
(
    ID INTEGER identity primary key,
    NAME VARCHAR(32) NOT NULL
);

create table CLIENT
(
    ID       INTEGER identity primary key,
    NAME     VARCHAR(32) not null,
    PHONE    VARCHAR(32) not null,
    EMAIL    VARCHAR(32) not null,
    PASSPORT VARCHAR(32) unique not null,
    BANK_ID  INTEGER NOT NULL,

    FOREIGN KEY (BANK_ID) references BANK(ID)

);

create table CREDIT
(
    ID INTEGER identity primary key,
    NAME   VARCHAR(32) not null,
    LIMIT INTEGER not null,
    PERCENT INTEGER not null,
    BANK_ID  INTEGER NOT NULL,

    FOREIGN KEY (BANK_ID) references BANK(ID)

);

create table CREDIT_OFFER
(
    ID INTEGER identity primary key,
    CLIENT_ID INTEGER not null,
    CREDIT_ID INTEGER not null,
    CREDIT_SUM INTEGER not null,
    DATE DATE not null,
    PAYMENT_SUM INTEGER,
    BODY_SUM INTEGER,
    PERCENTAGE_SUM INTEGER,

    FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT (ID),
    FOREIGN KEY (CREDIT_ID) REFERENCES CREDIT (ID)
)

