"""Put each DAG in a separated folder"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'dag_in_subfolder',
    default_args=default_args)

hello_task = BashOperator(
    task_id='hello_task',
    bash_command='echo Hello from The DAG in subfolder!',
    dag=dag)
