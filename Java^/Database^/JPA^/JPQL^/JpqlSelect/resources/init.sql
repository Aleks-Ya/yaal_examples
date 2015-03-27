DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS meal_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;

CREATE SEQUENCE user_id_seq START WITH 1;
CREATE TABLE users (
  id   INTEGER DEFAULT user_id_seq.nextval PRIMARY KEY,
  name VARCHAR(256),
);

CREATE SEQUENCE meal_id_seq START WITH 1;
CREATE TABLE meal (
  id      INTEGER DEFAULT meal_id_seq.nextval PRIMARY KEY,
  name    VARCHAR(256),
  user_id INTEGER,
  FOREIGN KEY (user_id) REFERENCES users (id)
);