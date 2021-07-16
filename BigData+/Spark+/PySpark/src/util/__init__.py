import os
import shutil

from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession


def create_spark_session(app_name: str) -> SparkSession:
    # os.environ['JAVA_HOME'] = '/home/aleks/.sdkman/candidates/java/8.0.222-zulu'
    python = shutil.which('python')
    os.environ['PYSPARK_PYTHON'] = python
    os.environ['PYSPARK_DRIVER_PYTHON'] = python
    return SparkSession \
        .builder \
        .appName(app_name) \
        .getOrCreate()


def create_spark_session_and_sql_contexts(app_name: str) -> (SparkSession, SQLContext):
    ss = create_spark_session(app_name)
    sql = SQLContext(ss)
    return ss, sql
