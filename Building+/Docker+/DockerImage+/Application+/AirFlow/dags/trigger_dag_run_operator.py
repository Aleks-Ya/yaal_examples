"""Trigger a DAG from another DAG using TriggerDagRunOperator"""
from datetime import datetime
from typing import Dict

from airflow import DAG
from airflow.models import DagRun
from airflow.operators.dagrun_operator import TriggerDagRunOperator, DagRunOrder
from airflow.operators.python_operator import PythonOperator

default_args = {
    'start_date': datetime.now(),
    'provide_context': True,
    'owner': 'airflow',
    'depends_on_past': False
}

target_dag_id = 'trigger_dag_run_operator_target'

target_dag = DAG(
    target_dag_id,
    # params={'message': 'my default message'},
    default_args=default_args)


def print_message(*op_args, **context):
    print(f"op_args: '{op_args}'")
    print(f"Context: '{context}'")
    # message: str = context['params']['message']
    # m: str = context['dag_run']["message"] if context["dag_run"]["message"] else None
    # message2: str = context['conf']["message"]
    # print(f"Message2: '{message2}'")
    dag_run: DagRun = context['dag_run']
    conf: Dict[str, object] = dag_run.conf
    print(f"Conf: '{conf}'")
    message: str = dag_run.conf["message"]
    print(f"Message: '{message}'")
    # print(f"M: '{m}'")


print_message_task = PythonOperator(
    task_id='print_message_task',
    python_callable=print_message,
    provide_context=True,
    dag=target_dag)

trigger_dag = DAG(
    'trigger_dag_run_operator_trigger',
    default_args=default_args)


def fill_target_dag_payload(context: Dict[str, object], order: DagRunOrder) -> DagRunOrder:
    print(f"Context: '{context}'")
    order.payload = {
        "message": 'hello from trigger dag'
    }
    print(f"DagRunOrder payload: '{order.payload}'")
    return order


trigger_dag_task = TriggerDagRunOperator(
    task_id='trigger_dag_task',
    trigger_dag_id=target_dag_id,
    python_callable=fill_target_dag_payload,
    dag=trigger_dag
)
