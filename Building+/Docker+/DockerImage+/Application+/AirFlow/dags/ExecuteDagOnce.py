"""
Execute a DAG once.
API: https://airflow.apache.org/code.html?highlight=branchpythonoperator#airflow.models.DAG
Doc: https://airflow.apache.org/scheduler.html#scheduling-triggers
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime(2015, 6, 1)
}

dag = DAG(
    'execute_dag_once',
    default_args=default_args,
    schedule_interval='@once')

first_task = BashOperator(
    task_id='first_task',
    bash_command='echo TASK_FIRST',
    dag=dag)

second_task = BashOperator(
    task_id='second_task',
    bash_command='echo TASK_SECOND',
    dag=dag)

first_task >> second_task
