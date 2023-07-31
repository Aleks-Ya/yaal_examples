-- IF function

SELECT IF(1>0, 'YES', 'NO');


SELECT name, IF(gender = 1, 'MALE', 'FEMALE') FROM (
    SELECT 'John' AS name, 1 AS gender
    UNION ALL
    SELECT 'Jane', 0
    UNION ALL
    SELECT 'Jim', 1
) AS t;

SELECT name, IF(gender IS NULL, 'NOT DEFINED', gender) FROM (
    SELECT 'John' AS name, 'M' AS gender
    UNION ALL
    SELECT 'Jane', 'F'
    UNION ALL
    SELECT 'Jim', NULL
) AS t2;