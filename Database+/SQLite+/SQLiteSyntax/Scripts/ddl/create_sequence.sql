--Create a sequence
WITH RECURSIVE sequence AS (
  SELECT 1 AS number
  UNION ALL
  SELECT number + 1
  FROM sequence
  WHERE number < 10
)
SELECT * FROM sequence;