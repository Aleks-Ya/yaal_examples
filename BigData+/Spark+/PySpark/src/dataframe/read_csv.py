from __future__ import print_function

from src.util import create_spark_and_sql_contexts

sparkContext, sqlContext = create_spark_and_sql_contexts(__file__)
df = sqlContext.read.option("encoding", "UTF-8").option("delimiter", ";").option('header', 'true').csv('people.csv')
df.show()
sparkContext.stop()
