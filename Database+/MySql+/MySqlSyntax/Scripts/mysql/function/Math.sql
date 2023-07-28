-- Mathematical functions

-- Modulo (get the remainder of a division operation)
SELECT 10 % 3;

-- Round
SELECT ROUND(123.456, 1);


-- SUM IF
SELECT SUM(performance > 5) FROM (
    SELECT 'John' AS name, 10 AS performance
    UNION ALL
    SELECT 'Jane', 5 
    UNION ALL
    SELECT 'Jim', 15
) AS t
-- Output: 2
