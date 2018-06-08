# Working with Variables.
# Set an usual variable named 'text_var' and a JSON variable 'json_var' before run.
# Doc: https://airflow.apache.org/concepts.html?highlight=variable#variables

from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime, timedelta
from airflow.models import Variable

text_variable = Variable.get("text_var")

# Getting a JSON var doesn't work ()
#json_variable = Variable.get("json_var", deserialize_json = True)

default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(2015, 6, 1),
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}

dag = DAG('varialbes', default_args=default_args)

# t1, t2 and t3 are examples of tasks created by instantiating operators
text_message = f"echo 'The text_var={text_variable}'"
#json_message = f"echo 'The json_var={json_variable}'"
t1 = BashOperator(
    task_id='text_variable',
    bash_command=text_message,
    dag=dag)

#t2 = BashOperator(
#    task_id='json_variable',
#    bash_command=json_message,
#    dag=dag)
