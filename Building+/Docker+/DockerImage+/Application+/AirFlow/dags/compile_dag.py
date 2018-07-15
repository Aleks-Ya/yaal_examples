"""
Executing code while AirFlow compiles a DAG.
Run "airflow list_dags" to see "print" output
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import PythonOperator
from datetime import datetime

print("Execute code from Python file")

def method():
    print("Executing a method")


def value():
    print("Assign a variable")

v = value()


def python_operator():
    print("Executing python_operator_task")

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'execute_code_while_compile',
    default_args=default_args)

python_operator_task = PythonOperator(
    task_id='python_operator_task',
    python_callable=python_operator,
    dag=dag)
