# Submits HelloWorld to a YARN cluster
# (Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop3+/HadoopCluster)
import os

from pyspark.sql import SparkSession

if __name__ == "__main__":
    os.environ["HADOOP_CONF_DIR"] = "/home/aleks/pr/home/yaal_examples/Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop3+/HadoopCluster/context"
    os.environ["HADOOP_USER_NAME"] = "root"
    ss = SparkSession \
        .builder \
        .appName('HelloWorldClusterYarn') \
        .master('yarn') \
        .getOrCreate()

    nums = [1, 2, 3, 4, 5]
    rdd = ss.sparkContext.parallelize(nums)
    nums_sum = rdd.sum()
    print(nums_sum)

    ss.stop()

    assert nums_sum == 15
