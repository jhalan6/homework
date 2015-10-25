CREATE DATABASE SPJDATABASE;
CREATE TABLE P(
        PNO CHAR(3) NOT NULL UNIQUE,
        PNAME CHAR(4),
        COLOR CHAR(1),
        WEIGHT INT,CHECK(WEIGHT>=0 AND WEIGHT<=100),
        PRIMARY KEY(PNO),
        );
CREATE TABLE S(
            SNO CHAR(3) NOT NULL UNIQUE,
            SNAME CHAR(5),
            STATUS INT,
            CITY CHAR(5),
            PRIMARY KEY(SNO)    
            );
CREATE TABLE J(
            JNO CHAR(3) NOT NULL UNIQUE,
            JNAME CHAR(5),
            CITY CHAR(5),
            PRIMARY KEY(JNO)
            );
CREATE TABLE SPJ(
            QTY INT,
            FOREIGN KEY(SNO) REFERENCES S(SNO),
            FOREIGN KEY(PNO) REFERENCES P(PNO),
            FOREIGN KEY(JNO) REFERENCES J(JNO),
            PRIMARY KEY(SNO,PNO,JNO),    
            );
