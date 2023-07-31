-- String functions
-- First character index is 1. 

-- Substring 
SELECT SUBSTRING('abcdefg', 2, 3), SUBSTRING('abc', 5, 3), SUBSTRING('abcdefg', 2);

-- Length
SELECT LENGTH('abcdefg');

-- Get N most left characters
SELECT LEFT('abcdefg', 2), LEFT('abc', 10);

-- Get N most right characters
SELECT RIGHT('abcdefg', 4), RIGHT('abc', 10);

-- Convert to lower or upper case
SELECT UPPER('AbcD'), LOWER('AbcD'), UCASE('AbcD'), LCASE('AbcD');

-- Concatenation
SELECT CONCAT('aa', 'bb', 'cc');

-- Fill string with a symbol until the string reaches desired length
SELECT LPAD('hi', 6, '='), LPAD(123, 6, 0); 
