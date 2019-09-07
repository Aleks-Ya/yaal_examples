"""Copy DAG programmically"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime
import copy

default_args = {
    'start_date': datetime.now()
}

dag_origin = DAG(
    'copy_dag_origin',
    default_args=default_args)

hello_task = BashOperator(
    task_id='hello_task',
    bash_command='echo Hello, World!',
    dag=dag_origin)


def copy_dag(dag: DAG, new_dag_id: str) -> DAG:
    new_dag = copy.deepcopy(dag)
    new_dag.dag_id = new_dag_id
    return new_dag

dag_copy = copy_dag(dag_origin, 'copy_dag_duplicate')
