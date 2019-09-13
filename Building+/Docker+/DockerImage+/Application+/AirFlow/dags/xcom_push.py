"""
Exchange messages between tasks via XCom
(push a message via xcom_push() method).
Doc: https://airflow.apache.org/concepts.html#xcoms
API:
    https://airflow.apache.org/code.html#airflow.models.TaskInstance.xcom_pull
    https://airflow.apache.org/code.html#airflow.models.TaskInstance.xcom_push
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from airflow.models import TaskInstance
from datetime import datetime

default_args = {
    'start_date': datetime(2015, 6, 1)
}

dag = DAG(
    'xcom_push',
    default_args=default_args,
    schedule_interval='@once')


def push_xcom(**context):
    context['task_instance'].xcom_push(key='my_key', value='my_value')


push_xcom_task = PythonOperator(
    task_id='push_xcom_task_by_method',
    python_callable=push_xcom,
    provide_context=True,
    dag=dag)


def pull_xcom(**context):
    value = context['task_instance'].xcom_pull(task_ids='push_xcom_task_by_method', key='my_key')
    print("Value: ", value)
    assert value == 'my_value'


pull_xcom_task = PythonOperator(
    task_id='pull_xcom_task',
    python_callable=pull_xcom,
    provide_context=True,
    dag=dag)

push_xcom_task >> pull_xcom_task
