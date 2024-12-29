# beeline CLI (Hive)

See also: Beeline queries examples

Help: `beeline --help`
Verbose: `beeline --verbose ...`
Connect with login and password: `beeline -u jdbc:hive2://localhost:10000 -n my_login -p my_pass`
Connect anonymously: `beeline -u jdbc:hive2://localhost:10000 -n '' -p ''`



## Beeline queries examples
```sql
SELECT COUNT(*) FROM flights; --7 453 216
SELECT COUNT(*) FROM flights WHERE cancelled = TRUE; -- 160 748 
SELECT COUNT(*) FROM flights WHERE cancelled = TRUE AND year=2007; -- 160 748 
SELECT COUNT(*) FROM cancel2007; -- 160 748 
SELECT COUNT(*) FROM cancelCarriers; -- 20
SELECT COUNT(DISTINCT uniqueCarrier) FROM cancel2007; -- 20

SELECT * FROM cancel2007 LIMIT 5;
SELECT * FROM cancelCarriers LIMIT 5;
SELECT COUNT(*) FROM cancelled WHERE uniqueCarrier='MQ'; -- 22 792
SELECT COUNT(*) FROM flights WHERE uniqueCarrier='MQ'; -- 540 494
SELECT COUNT(DISTINCT(city)) FROM airports;
SELECT COUNT(DISTINCT(city)) FROM airports; -- 2 677


CREATE TEMPORARY TABLE cancelled_flights AS 
SELECT uniqueCarrier, origin 
FROM flights 
WHERE cancelled = TRUE AND year=2007;


CREATE TEMPORARY TABLE cancelled_cities AS 
SELECT uniqueCarrier, city 
FROM cancelled_flights
JOIN airports ON origin=iata;

CREATE TEMPORARY TABLE cancelled_cities AS 
SELECT uniqueCarrier, CONCAT_WS(',',COLLECT_LIST(DISTINCT city)) AS cities, COUNT(uniqueCarrier) AS cancellations
FROM cancelled_flights
JOIN airports ON origin=iata 
GROUP BY uniqueCarrier
HAVING cancellations > 1;

SELECT description AS Carrier, cancellations AS `Cancelled flights`, Cities
FROM cancelled_cities
JOIN carriers ON uniqueCarrier=code 
ORDER BY `Cancelled flights` DESC;

DROP TABLE cancelled_flights;
DROP TABLE cancelled_cities;
```
