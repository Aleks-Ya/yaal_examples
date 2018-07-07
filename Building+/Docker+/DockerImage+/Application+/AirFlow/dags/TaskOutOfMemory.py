"""
Out of memory in a task.
API: https://airflow.apache.org/code.html#airflow.models.BaseOperator
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime
import os
import psutil

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'out_of_memory',
    default_args=default_args)

def memory_usage_megabytes():
    process = psutil.Process(os.getpid())
    mem = process.memory_info().rss / 1000000
    return mem

memory_usage_limit_megabytes = 1000

def oom():
    print("Start eating memory...")
    print("Memory limit (MB): ", memory_usage_limit_megabytes)
    big_list = []
    last_memory_usage = 0
    while last_memory_usage < memory_usage_limit_megabytes:
        big_list.append(datetime.now())
        if len(big_list) % 1000000 == 0:
            last_memory_usage = memory_usage_megabytes()
            print("Element count: ", len(big_list), ". Memory usage (MB): ", last_memory_usage)
    print("The limit is exceeded")

out_of_memory_task = PythonOperator(
    task_id='out_of_memory_task',
    python_callable=oom,
    dag=dag)
