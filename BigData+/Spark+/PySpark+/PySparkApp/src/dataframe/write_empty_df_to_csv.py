from __future__ import print_function

import os
import tempfile

from pyspark.sql.types import StructType, StructField, IntegerType, StringType, LongType

from src.util import create_spark_session_and_sql_contexts

ss, sqlContext = create_spark_session_and_sql_contexts(__file__)

schema = StructType([
    StructField("id", LongType(), False),
    StructField("name", StringType(), False),
    StructField("age", IntegerType(), True)])
values = []

df = sqlContext.createDataFrame(values, schema)
df.show()

temp_dir: str = tempfile.mkdtemp()
output_file = os.path.join(temp_dir, 'utf-8.csv')
df.write.option('header', 'true').csv(output_file)
ss.stop()

print(f'Output file: "{output_file}"')
