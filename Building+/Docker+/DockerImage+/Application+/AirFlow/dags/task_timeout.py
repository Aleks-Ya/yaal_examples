"""
Exceed a task timeout.
API: https://airflow.apache.org/code.html#airflow.models.BaseOperator
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime, timedelta
import time

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'task_timeout',
    default_args=default_args)


def sleep():
    print("Sleeping 5 seconds...")
    time.sleep(5)


timeout_exceed_task = PythonOperator(
    task_id='timeout_exceed_task',
    python_callable=sleep,
    dag=dag,
    execution_timeout=timedelta(seconds=3))
