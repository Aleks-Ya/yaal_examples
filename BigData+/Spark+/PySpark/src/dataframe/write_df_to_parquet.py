from __future__ import print_function

import os
import tempfile

from src.util import create_spark_and_sql_contexts

sparkContext, sqlContext = create_spark_and_sql_contexts(__file__)

columns = ['id', 'dogs', 'cats']
values = [
    (1, 2, 0),
    (2, 0, 1)
]

df = sqlContext.createDataFrame(values, columns)

df.show()
temp_dir: str = tempfile.mkdtemp()
output_file = os.path.join(temp_dir, 'utf-8.parquet')
df.write.option('header', 'true').parquet(output_file)
sparkContext.stop()

print(f'Output file: "{output_file}"')
