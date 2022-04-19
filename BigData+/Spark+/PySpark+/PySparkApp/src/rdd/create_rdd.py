from __future__ import print_function

from src.util import create_spark_session

ss = create_spark_session(__file__)
nums = [1, 10, 100]
rdd = ss.sparkContext.parallelize(nums)
nums_sum = rdd.sum()
ss.stop()
assert nums_sum == 111
