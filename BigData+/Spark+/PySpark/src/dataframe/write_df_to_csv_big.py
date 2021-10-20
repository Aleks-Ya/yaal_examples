from __future__ import print_function

from src.util import create_spark_session_and_sql_contexts

ss, sqlContext = create_spark_session_and_sql_contexts(__file__, configs={"spark.driver.memory": "2g"})

column_number = 30000
value_number = 100
columns = [f'col{col}' for col in range(0, column_number)]
values = [[f'val_{c}_{v}' for c in columns] for v in range(0, value_number)]

sqlContext.createDataFrame(values, columns) \
    .write \
    .option('header', 'true') \
    .mode("overwrite") \
    .csv('/tmp/write_df_to_csv_big')

ss.stop()
