CREATE TABLE logins (
    login VARCHAR2(50), 
    registration DATE,
    last_activity TIMESTAMP
);
INSERT INTO logins(login, registration, last_activity) VALUES('john', DATE '2020-05-18', TIMESTAMP '2021-01-16 15:35:55');
SELECT * FROM logins;