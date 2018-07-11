"""Hello World DAG"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'hello_world',
    default_args=default_args)

hello_task = BashOperator(
    task_id='hello_task',
    bash_command='echo Hello, World!',
    dag=dag)
