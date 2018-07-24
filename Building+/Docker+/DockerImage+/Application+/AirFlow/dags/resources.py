"""
Allocate resources to a task (memory, disk, CPU, GPU).
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import PythonOperator
from datetime import datetime


default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'resources',
    default_args=default_args)

res = {
    'cpus': 1,
    'ram': 1024,
    'disk': 1024,
    'gpus': 0
}

bash_task = BashOperator(
    task_id='bash_task',
    bash_command='date',
    dag=dag,
    resources=res)

def print_date():
    print("Current date: ", datetime.now())

python_task =  PythonOperator(
    task_id='python_operator_task',
    python_callable=print_date,
    dag=dag,
    resources=res)

bash_task >> python_task
