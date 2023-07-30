-- Regular expressions

-- WHERE REGEXP 
SELECT email FROM (
    SELECT 'John@gmail.com' AS email
    UNION ALL
    SELECT 'Jane@hotmail.com'
    UNION ALL
    SELECT 'Jim@gmail.com'
) AS t
WHERE email REGEXP '.*@gmail\\.com';
