DROP TABLE ADDRESS_T;
DROP TABLE MEMBER_T;

CREATE TABLE MEMBER_T (
    MEMBER_NO NUMBER             NOT NULL,
    EMAIL     VARCHAR2(100 BYTE) NOT NULL UNIQUE,
    NAME      VARCHAR2(100 BYTE),
    GENDER    VARCHAR2(5 BYTE),
    CONSTRAINT PK_MEMBER PRIMARY KEY(MEMBER_NO)
);

CREATE TABLE ADDRESS_T (
  ADDRESS_NO     NUMBER NOT NULL,
  ZONECODE       CHAR(5 BYTE),
  ADDRESS        VARCHAR2(100 BYTE),
  DETAIL_ADDRESS VARCHAR2(100 BYTE),
  EXTRA_ADDRESS  VARCHAR2(100 BYTE),
  MEMBER_NO      NUMBER,
  CONSTRAINT PK_ADDRESS PRIMARY KEY(ADDRESS_NO),
  CONSTRAINT FK_ADDRESS_NO FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE
);

DROP SEQUENCE MEMBER_SEQ;
DROP SEQUENCE ADDRESS_SEQ;
CREATE SEQUENCE MEMBER_SEQ NOCACHE;
CREATE SEQUENCE ADDRESS_SEQ NOCACHE;