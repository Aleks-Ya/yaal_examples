# Working with Connections.
# Doc: https://airflow.apache.org/concepts.html#connections
# Uses predefined HTTP connection "http_default"

from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime, timedelta
from airflow.hooks.http_hook import HttpHook

default_args = {
    'start_date': datetime.now(),
}

dag = DAG(
    'connections',
    default_args=default_args,
    schedule_interval='@once')

def read_connection(**context):
    context['task_instance'].xcom_push(key='my_key', value='my_value')
    hook = HttpHook(http_conn_id='http_default')
    session = hook.get_conn(None)
    print("requests.sessions.Session: ", session)

read_connection_task = PythonOperator(
    task_id='read_connection_task',
    python_callable=read_connection,
    provide_context=True,
    dag=dag)
