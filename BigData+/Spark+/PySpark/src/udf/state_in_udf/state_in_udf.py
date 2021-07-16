from __future__ import print_function

import pandas as pd
from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType, FloatType, ArrayType, StringType

from util import create_spark_session

ss = create_spark_session(__file__)

df_pd = pd.DataFrame(
    data={'integers': [1, 2, 3]}
)
df = ss.createDataFrame(df_pd)
df.printSchema()
df.show()


class MyData:

    def __init__(self) -> None:
        super().__init__()
        self.state = 'original state'

    def set_state(self, new_state):
        self.state = new_state

    def udf_work(self, row):
        return f'{self.state} - {row}'


my_data = MyData()
my_data.set_state('new state')

udf_int = udf(my_data.udf_work, StringType())

converted_df = df.select('integers', udf_int('integers').alias('int_processed'))

converted_df.show()

ss.stop()
