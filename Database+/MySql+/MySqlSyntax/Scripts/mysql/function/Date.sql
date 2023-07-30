-- Date functions

-- Between two dates
SELECT '2023-06-01' BETWEEN '2023-01-01' AND '2023-12-31';
SELECT '1999-06-01' BETWEEN '2023-01-01' AND '2023-12-31';

-- Substract dates
SELECT DATE_SUB('2023-06-01', INTERVAL 5 DAY);

-- Current date
SELECT CURDATE();

-- Get parts of a date
SELECT YEAR('2020-06-01'), MONTH('2020-06-01');
