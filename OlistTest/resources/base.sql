CREATE TABLE PRODUCT (
ID INTEGER PRIMARY KEY,
NAME VARCHAR(200),
DESCRIPTION VARCHAR(200),
VALUE DOUBLE
);

CREATE TABLE CATEGORY (
ID INTEGER PRIMARY KEY,
NAME VARCHAR(200)
);

CREATE TABLE CATEGORY_PRODUCT (
ID_CATEGORY INTEGER,
ID_PRODUCT INTEGER 
);


CREATE SEQUENCE PRODUCT_SEQUENCE START WITH 0;

CREATE SEQUENCE CATEGORY_SEQUENCE START WITH 0;

