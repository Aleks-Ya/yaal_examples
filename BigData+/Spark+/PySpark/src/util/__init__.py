import os
import shutil

from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession


def create_spark_context(app_name: str) -> SparkSession:
    # os.environ['JAVA_HOME'] = '/home/aleks/.sdkman/candidates/java/8.0.222-zulu'
    python = shutil.which('python')
    os.environ['PYSPARK_PYTHON'] = python
    os.environ['PYSPARK_DRIVER_PYTHON'] = python

    sc = SparkSession \
        .builder \
        .appName(app_name) \
        .getOrCreate()
    return sc


def create_spark_and_sql_contexts(app_name: str) -> (SparkSession, SQLContext):
    sc = create_spark_context(app_name)
    sql = SQLContext(sc)
    return sc, sql
