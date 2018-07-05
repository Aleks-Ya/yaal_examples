"""
Allocate resources to a task (memory, disk, CPU, GPU).
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime


default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'Resources',
    default_args=default_args,
    schedule_interval='@once'
)

res = {
    'cpus': 1,
    'ram': 1024,
    'disk': 1024,
    'gpus': 0
}

the_task = BashOperator(
    task_id='the_task',
    bash_command='date',
    dag=dag,
    resources=res)
