# 001-create-parquet-file

## Task
Create a Parquet file containing this data:
```
name	age	married	rating
John	30	true	0.95
Mary	25	false	0.90
```
Variants:
1. Using Python (`pandas`)
2. Using Java
3. Using Scala Spark

## Steps
Python: see `BigData+/Spark+/PySpark+/PySparkApp/src/dataframe/parquet/write_df_to_parquet.py`
Scala: see `Scala+/ScalaSbt/Spark+/Spark3+/Spark3Sql/src/test/scala/dataset/create/parquet/WriteDsToParquet.scala`

## Python
Setup:
1. Install package: `pip install pandas`
2. Createm file `create.py`:
```
import pandas as pd

df = pd.DataFrame({'name': ['John', 'Mary'], 'age': [30, 25], 'married': [True, False], 'rating': [0.95, 0.90]})
df.to_parquet('people.parquet')
```
3. Execute: `python create.py`
4. Check rows: `parquet-tools show people.parquet`
5. Check schema: `parquet-tools inspect people.parquet`

Cleanup: not need
