"""
Skip tasks using BranchPythonOperator and DummyOperator.
API:
https://airflow.apache.org/code.html?highlight=branchpythonoperator#airflow.operators.BranchPythonOperator
https://airflow.apache.org/code.html#airflow.operators.DummyOperator
Doc: https://airflow.apache.org/concepts.html?highlight=branchpythonoperator#branching
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.dummy_operator import DummyOperator
from airflow.operators.python_operator import BranchPythonOperator
from datetime import datetime

default_args = {
    'start_date': datetime(2015, 6, 1)
}

dag = DAG(
    'skip_tasks',
    default_args=default_args,
    schedule_interval='@once')

first_task = BashOperator(
    task_id='first_task',
    bash_command='echo TASK_FIRST',
    dag=dag)

def decide_which_path():
    return "dummy_task"

branching_task = BranchPythonOperator(
    task_id='branching_task',
    python_callable=decide_which_path,
    dag=dag)

skipped_task = BashOperator(
    task_id='skipped_task',
    bash_command='echo TASK_SKIPPED',
    dag=dag)

dummy_task = DummyOperator(
    task_id='dummy_task',
    dag=dag)

join_task = BashOperator(
    task_id='join_task',
    bash_command='echo TASK_JOIN',
    trigger_rule='one_success',
    dag=dag)

first_task >> branching_task
branching_task >> skipped_task >> join_task
branching_task >> dummy_task >> join_task
