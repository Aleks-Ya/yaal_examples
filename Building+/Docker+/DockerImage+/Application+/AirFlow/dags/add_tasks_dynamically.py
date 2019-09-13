"""
Add new tasks to a DAG at runtime.
THIS APPROACH DOESN'T WORK!
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'add_tasks_dynamically',
    default_args=default_args)


def task_2_callable():
    print("TASK #2")


def add_task_callable():
    print("Add task")
    add_task()


task_1 = PythonOperator(
    task_id='task_1',
    python_callable=add_task_callable,
    dag=dag)


def add_task():
    print("Try to add new operator to the DAG")
    task_2 = PythonOperator(
        task_id='task_2',
        python_callable=task_2_callable,
        dag=dag)
    task_1.set_downstream(task_2)
