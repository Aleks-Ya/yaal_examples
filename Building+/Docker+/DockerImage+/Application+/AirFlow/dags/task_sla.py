"""
NOT WORK
Exceed a task SLA.
API: https://airflow.apache.org/code.html#airflow.models.BaseOperator
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime, timedelta
import time

default_args = {
    'start_date': datetime.now()
}


def print_sla_miss(dag, task_list, blocking_task_list, slas, blocking_tis):
    print(
        "SLA was missed on DAG %(dag)s by task id %(blocking_tis)s with task list %(task_list)s which are blocking %(blocking_task_list)s")


dag = DAG(
    'task_sla',
    default_args=default_args,
    sla_miss_callback=print_sla_miss,
    schedule_interval='@once')


def sleep():
    print("Sleeping 5 seconds...")
    time.sleep(5)


sla_exceed_task = PythonOperator(
    task_id='sla_exceed_task',
    python_callable=sleep,
    dag=dag,
    sla=timedelta(seconds=3))
