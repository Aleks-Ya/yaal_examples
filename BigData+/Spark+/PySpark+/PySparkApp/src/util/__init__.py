import os
import shutil
from typing import Dict

from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession


def find_python() -> str:
    names = ['python3', 'python']
    for name in names:
        python = shutil.which(name)
        if python is not None:
            return python
    raise Exception('Python executable not found')


def create_spark_session(app_name: str, configs: Dict[str, str] = None) -> SparkSession:
    # os.environ['JAVA_HOME'] = '/home/aleks/.sdkman/candidates/java/8.0.222-zulu'
    python = find_python()
    os.environ['PYSPARK_PYTHON'] = python
    os.environ['PYSPARK_DRIVER_PYTHON'] = python
    builder = SparkSession.builder.appName(app_name)
    if configs is not None:
        for k, v in configs.items():
            builder.config(k, v)
    return builder.getOrCreate()


def create_spark_session_and_sql_contexts(app_name: str, configs: Dict[str, str] = None) -> (SparkSession, SQLContext):
    ss = create_spark_session(app_name, configs)
    sql = SQLContext(ss.sparkContext)
    return ss, sql
