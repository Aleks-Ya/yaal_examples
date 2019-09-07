"""Hello World DAG"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag1 = DAG(
    'two_dags_in_one_file_1',
    default_args=default_args)

hello_task1 = BashOperator(
    task_id='hello_task',
    bash_command='echo Hello, World!',
    dag=dag1)

dag2 = DAG(
    'two_dags_in_one_file_2',
    default_args=default_args)

hello_task2 = BashOperator(
    task_id='hello_task',
    bash_command='echo Hello, World!',
    dag=dag2)
