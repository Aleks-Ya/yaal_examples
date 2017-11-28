# View
"Indexed view" = "Materialized view".

## Create regular view
`CREATE VIEW view1 AS SELECT * FROM t1`

## Create indexed (Materialized) view
```
SET QUOTED_IDENTIFIER ON;
CREATE VIEW dbo.view2 WITH SCHEMABINDING AS SELECT a FROM dbo.t1;
CREATE UNIQUE CLUSTERED INDEX view2_ind ON view2(col1, col2);  
```

## Show views
In current database:
`SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW'`

In database `db1`:
`SELECT TABLE_NAME FROM db1.INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW'`

## Drop view
`DROP VIEW view2`

## Show view size
`sp_spaceused 'ViewName'`
