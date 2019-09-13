"""
Use PythonOperator context (**kwargs).
"""
from datetime import datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'python_operator_context',
    default_args=default_args)


def use_context(**context):
    print(f"Context: {context}")


the_task = PythonOperator(
    task_id='print_context_task',
    python_callable=use_context,
    provide_context=True,
    dag=dag)
