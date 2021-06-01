import os

from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession


def create_spark_context(app_name: str) -> SparkSession:
    # os.environ['JAVA_HOME'] = '/home/aleks/.sdkman/candidates/java/8.0.222-zulu'
    os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'
    os.environ['PYSPARK_DRIVER_PYTHON'] = '/usr/bin/python3'

    sc = SparkSession \
        .builder \
        .appName(app_name) \
        .getOrCreate()
    return sc


def create_spark_and_sql_contexts(app_name: str) -> (SparkSession, SQLContext):
    sc = create_spark_context(app_name)
    sql = SQLContext(sc)
    return sc, sql
