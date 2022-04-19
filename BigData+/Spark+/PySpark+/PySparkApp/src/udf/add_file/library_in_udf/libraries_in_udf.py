from __future__ import print_function

import pandas as pd
from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType

from udf.add_file.library_in_udf.my_functions import square
from udf.add_file.library_in_udf.my_lib.my_lib import to_upper_case
from util import create_spark_session

ss = create_spark_session(__file__)
# spark.sparkContext.addPyFile('')

df_pd = pd.DataFrame(
    data={'integers': [1, 2, 3]}
)
df = ss.createDataFrame(df_pd)
df.printSchema()
df.show()


def my_udf(num: int):
    print(to_upper_case('a'))
    return square(num) * 3


square_udf_int = udf(my_udf, IntegerType())

converted_df = df.select('integers', square_udf_int('integers').alias('int_squared'))

converted_df.show()

ss.stop()
