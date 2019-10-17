from __future__ import print_function

import pandas as pd
from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType, FloatType, ArrayType

from util import create_spark_context

spark = create_spark_context(__file__)

df_pd = pd.DataFrame(
    data={'integers': [1, 2, 3],
          'floats': [-1.0, 0.5, 2.7],
          'integer_arrays': [[1, 2], [3, 4, 5], [6, 7, 8, 9]]}
)
df = spark.createDataFrame(df_pd)
df.printSchema()
df.show()


def square(x):
    return x ** 2


def square_list(x):
    return [float(val) ** 2 for val in x]


square_udf_int = udf(square, IntegerType())
square_udf_float = udf(square, FloatType())
square_list_udf = udf(square_list, ArrayType(FloatType()))

converted_df = df.select('integers', 'floats', 'integer_arrays',
                         square_udf_int('integers').alias('int_squared'),
                         square_udf_float('floats').alias('float_squared'),
                         square_list_udf('integer_arrays').alias('integer_array_squared'))

converted_df.show()

spark.stop()
