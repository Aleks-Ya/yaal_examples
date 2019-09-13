"""
Working with Variables.
Doc: https://airflow.apache.org/concepts.html?highlight=variable#variables
"""

from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime
from airflow.models import Variable

text_variable = Variable.get("user")

# Getting a JSON var doesn't work ()
# json_variable = Variable.get("json_var", deserialize_json = True)

default_args = {
    'start_date': datetime.now()
}

dag = DAG('varialbes', default_args=default_args)

text_message = f"echo 'The user variable is {text_variable}'"
# json_message = f"echo 'The json_var={json_variable}'"
t1 = BashOperator(
    task_id='text_variable',
    bash_command=text_message,
    dag=dag)
