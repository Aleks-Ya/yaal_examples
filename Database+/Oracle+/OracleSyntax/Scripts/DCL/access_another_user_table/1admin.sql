-- Create user with password
DROP USER user1;
CREATE USER user1 IDENTIFIED BY pass1;
GRANT CREATE SESSION TO user1;
GRANT CREATE TABLE TO user1;
ALTER USER user1 QUOTA 50m on SYSTEM;

DROP USER user2;
CREATE USER user2 IDENTIFIED BY pass2;
GRANT CREATE SESSION TO user2;
GRANT CREATE TABLE TO user2;
ALTER USER user2 QUOTA 50m on SYSTEM;
GRANT DROP ANY TABLE TO user2;

DROP USER user3;
CREATE USER user3 IDENTIFIED BY pass3;


REVOKE CREATE SESSION FROM user1;
