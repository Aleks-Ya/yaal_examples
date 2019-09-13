"""
Working with ShortCircuitOperator.
API: https://airflow.readthedocs.io/en/latest/code.html?highlight=ShortCircuitOperator#airflow.operators.python_operator.ShortCircuitOperator
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import ShortCircuitOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'short_circuit_operator',
    default_args=default_args)


def is_need_continue_true():
    return True


def is_need_continue_false():
    return False


short_circuit_continue_task = ShortCircuitOperator(
    task_id='short_circuit_continue_task',
    python_callable=is_need_continue_true,
    dag=dag)

short_circuit_skip_task = ShortCircuitOperator(
    task_id='short_circuit_skip_task',
    python_callable=is_need_continue_false,
    dag=dag)

skipped_task = BashOperator(
    task_id='skipped_task',
    bash_command='echo skipped_task',
    dag=dag)

continue_task = BashOperator(
    task_id='continue_task',
    bash_command='echo "Hi, continue_task!"',
    dag=dag)

short_circuit_continue_task >> continue_task
short_circuit_skip_task >> skipped_task
