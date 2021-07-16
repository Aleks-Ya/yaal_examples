import os
import shutil

from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession


def find_python() -> str:
    names = ['python3', 'python']
    for name in names:
        python = shutil.which(name)
        if python is not None:
            return python
    raise Exception('Python executable not found')


def create_spark_session(app_name: str) -> SparkSession:
    # os.environ['JAVA_HOME'] = '/home/aleks/.sdkman/candidates/java/8.0.222-zulu'
    python = find_python()
    os.environ['PYSPARK_PYTHON'] = python
    os.environ['PYSPARK_DRIVER_PYTHON'] = python
    return SparkSession \
        .builder \
        .appName(app_name) \
        .getOrCreate()


def create_spark_session_and_sql_contexts(app_name: str) -> (SparkSession, SQLContext):
    ss = create_spark_session(app_name)
    sql = SQLContext(ss.sparkContext)
    return ss, sql
