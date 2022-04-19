# Write a dataframe as several parquet files and read them back
from __future__ import print_function

import os
import tempfile

from src.util import create_spark_session_and_sql_contexts

ss, sqlContext = create_spark_session_and_sql_contexts(__file__)

columns = ['id', 'name', 'age', 'gender']
values = [
    (1, 'John', 30, 'M'),
    (2, 'Mary', 25, 'F'),
    (3, 'Mark', 20, 'M'),
    (4, 'Petr', 15, 'M'),
    (5, 'Sam', 10, 'M')
]

df = sqlContext.createDataFrame(values, columns).repartition('gender')

df.show()
temp_dir: str = tempfile.mkdtemp()
output_dir = os.path.join(temp_dir, 'people')
df.write.option('header', 'true').parquet(output_dir)

print(f'Output dir: "{output_dir}"')
files = os.listdir(output_dir)
print(f'Output files: {files}')

df2 = sqlContext.read.parquet(output_dir)
df2.show()

diff = df2.exceptAll(df)
assert diff.count() == 0

ss.stop()
