# Variable

## Declare variable
```
DECLARE @myNumber INT = 10;
PRINT @myNumber;
GO
```

## Change variable's value
```
DECLARE @myNumber INT = 10;
PRINT @myNumber;
SET @myNumber = 20;
PRINT @myNumber;
GO
```

## Show object size
`sp_spaceused 'TableName'`
