"""
Use PathonOperator.
API: https://airflow.readthedocs.io/en/latest/code.html#airflow.operators.python_operator.PythonOperator
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'python_operator',
    default_args=default_args)


def print_date():
    print("Current date: ", datetime.now())


python_operator_task = PythonOperator(
    task_id='python_operator_task',
    python_callable=print_date,
    dag=dag)
