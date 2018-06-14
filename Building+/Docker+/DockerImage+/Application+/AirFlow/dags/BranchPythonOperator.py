"""
Working with BranchPythonOperator.
API: https://airflow.apache.org/code.html?highlight=branchpythonoperator#airflow.operators.BranchPythonOperator
Doc: https://airflow.apache.org/concepts.html?highlight=branchpythonoperator#branching
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import BranchPythonOperator
from datetime import datetime

default_args = {
    'start_date': datetime(2015, 6, 1)
}

dag = DAG(
    'branch_python_operator',
    default_args=default_args,
    schedule_interval='@once')

first_task = BashOperator(
    task_id='first_task',
    bash_command='echo TASK_FIRST',
    dag=dag)

def decide_which_path():
    something = True
    if something is True:
        return "branch_a_task"
    else:
        return "branch_b_task"

branching_task = BranchPythonOperator(
    task_id='branching_task',
    python_callable=decide_which_path,
    dag=dag)

branch_a_task = BashOperator(
    task_id='branch_a_task',
    bash_command='echo TASK_A',
    dag=dag)

branch_b_task = BashOperator(
    task_id='branch_b_task',
    bash_command='echo TASK_B',
    dag=dag)

first_task >> branching_task
branching_task >> branch_a_task
branching_task >> branch_b_task
