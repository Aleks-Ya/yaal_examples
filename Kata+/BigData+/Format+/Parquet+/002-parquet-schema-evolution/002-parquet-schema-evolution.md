# 002-parquet-schema-evolution

## Task
Give a Parquet file of structure:
```
name	age	married	rating
John	30	true	0.95
Mary	25	false	0.90
```
1. Rename column `name` to `identity`
2. Remove column `rating`
3. Convert type `age` column to Double
4. Add column `City` with default value `London`

## Setup
