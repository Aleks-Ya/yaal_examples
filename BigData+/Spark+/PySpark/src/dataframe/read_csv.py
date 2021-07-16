from __future__ import print_function

from src.util import create_spark_session_and_sql_contexts

ss, sqlContext = create_spark_session_and_sql_contexts(__file__)
df = sqlContext.read.option("encoding", "UTF-8").option("delimiter", ";").option('header', 'true').csv('people.csv')
df.show()
ss.stop()
