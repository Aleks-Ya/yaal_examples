-- As "user1"

-- Create table
CREATE TABLE user1.cities (
    city VARCHAR2(50), 
    established NUMBER(4)
);

-- Write into owned table
INSERT INTO user1.cities(city, established) VALUES ('Moscow', 1147);
INSERT INTO user1.cities(city, established) VALUES ('SPb', 1703);

-- Read owned table
SELECT * FROM user1.cities;


GRANT SELECT ON USER1.CITIES TO user2;
REVOKE SELECT ON USER1.CITIES FROM user2;

GRANT INSERT ON USER1.CITIES TO user2;
REVOKE INSERT ON USER1.CITIES FROM user2;

GRANT DROP ON USER1.CITIES TO user2;
REVOKE DELETE ON USER1.CITIES FROM user2;

