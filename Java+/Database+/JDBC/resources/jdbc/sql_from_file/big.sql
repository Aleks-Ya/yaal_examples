--Комментарий
DROP TABLE IF EXISTS execute_sql;
CREATE TABLE execute_sql (
  id   INTEGER,
  city CHAR(20)
);
INSERT INTO execute_sql VALUES (1, 'СПБ');
INSERT INTO execute_sql VALUES (2, 'Вологда');
INSERT INTO execute_sql VALUES (3, 'Москва');