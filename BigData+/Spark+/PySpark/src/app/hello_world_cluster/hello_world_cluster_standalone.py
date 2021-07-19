# Submits HelloWorld to a Standalone Spark cluster
# (Building+/Docker+/DockerImage+/Application+/Spark+/SparkStandaloneDocker)
from pyspark.sql import SparkSession

if __name__ == "__main__":
    ss = SparkSession \
        .builder \
        .appName('HelloWorldClusterStandalone') \
        .master('spark://spark-standalone-cluster-master:7077') \
        .getOrCreate()

    nums = [1, 2, 3, 4, 5]
    rdd = ss.sparkContext.parallelize(nums)
    nums_sum = rdd.sum()

    ss.stop()

    assert nums_sum == 15
