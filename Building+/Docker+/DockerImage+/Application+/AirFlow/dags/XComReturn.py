"""
Exchange messages between tasks via XCom
(push a message via return a value from the push task).
Doc: https://airflow.apache.org/concepts.html#xcoms
"""
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime

default_args = {
    'start_date': datetime(2015, 6, 1)
}

dag = DAG(
    'xcom_return',
    default_args=default_args,
    schedule_interval='@once')

def push_xcom():
    return 'abc'

push_xcom_task = PythonOperator(
    task_id='push_xcom_task_by_return',
    python_callable=push_xcom,
    dag=dag)

def pull_xcom(**context):
    value = context['task_instance'].xcom_pull(task_ids='push_xcom_task_by_return')
    print("Value: ", value)
    assert value == 'abc'

pull_xcom_task = PythonOperator(
    task_id='pull_xcom_task',
    python_callable=pull_xcom,
    provide_context=True,
    dag=dag)

push_xcom_task >> pull_xcom_task
