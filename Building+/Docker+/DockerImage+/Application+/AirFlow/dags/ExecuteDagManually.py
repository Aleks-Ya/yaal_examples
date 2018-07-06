"""
Execute a DAG only manually.
API: https://airflow.apache.org/code.html?highlight=branchpythonoperator#airflow.models.DAG
Doc: https://airflow.apache.org/scheduler.html#scheduling-triggers
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'execute_dag_manually',
    default_args=default_args)

the_task = BashOperator(
    task_id='the_task',
    bash_command='echo THE_TASK',
    dag=dag)
