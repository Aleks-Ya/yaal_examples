from __future__ import print_function

from src.util import create_spark_and_sql_contexts

sparkContext, sqlContext = create_spark_and_sql_contexts(__file__)

columns = ['id', 'dogs', 'cats']
values = [
    (1, 2, 0),
    (2, 0, 1)
]

df = sqlContext.createDataFrame(values, columns)

df.show()

sparkContext.stop()
