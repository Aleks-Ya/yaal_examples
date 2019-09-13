"""Trigger a DAG from another DAG using TriggerDagRunOperator"""
from datetime import datetime
from typing import Dict

from airflow import DAG
from airflow.operators.dagrun_operator import TriggerDagRunOperator, DagRunOrder
from airflow.operators.python_operator import PythonOperator
from airflow.utils.decorators import apply_defaults

default_args = {
    'start_date': datetime.now(),
}

target_dag_id = 'trigger_dag_run_operator_debug_target'

target_dag = DAG(target_dag_id,
                 default_args=default_args)


def print_message(*op_args, **context):
    print("I'm triggered")


class DebugPythonOperator(PythonOperator):

    @apply_defaults
    def __init__(self,
                 python_callable,
                 op_args=None,
                 op_kwargs=None,
                 provide_context=False,
                 templates_dict=None,
                 templates_exts=None,
                 *args, **kwargs):
        super(DebugPythonOperator, self).__init__(
            python_callable=python_callable,
            op_args=op_args,
            op_kwargs=op_kwargs,
            provide_context=provide_context,
            templates_dict=templates_dict,
            templates_exts=templates_exts,
            *args,
            **kwargs
        )

    def execute(self, context):
        import pydevd_pycharm
        pydevd_pycharm.settrace('172.17.0.1', port=1092, stdoutToServer=True, stderrToServer=True)
        return super().execute(context)


print_message_task = DebugPythonOperator(
    task_id='print_message_task',
    python_callable=print_message,
    provide_context=True,
    dag=target_dag)

trigger_dag = DAG(
    'trigger_dag_run_operator_debug_trigger',
    default_args=default_args)


def fill_order(context: Dict[str, object], order: DagRunOrder) -> DagRunOrder:
    return order


class DebugTriggerDagRunOperator(TriggerDagRunOperator):

    @apply_defaults
    def __init__(self,
                 task_id,
                 trigger_dag_id,
                 python_callable,
                 dag,
                 *args, **kwargs):
        super(DebugTriggerDagRunOperator, self).__init__(
            task_id=task_id,
            trigger_dag_id=trigger_dag_id,
            python_callable=python_callable,
            dag=dag,
            *args,
            **kwargs
        )

    def execute(self, context):
        import pydevd_pycharm
        pydevd_pycharm.settrace('172.17.0.1', port=1092, stdoutToServer=True, stderrToServer=True)
        return super().execute(context)


trigger_dag_task = DebugTriggerDagRunOperator(
    task_id='trigger_dag_task',
    trigger_dag_id=target_dag_id,
    python_callable=fill_order,
    dag=trigger_dag
)
