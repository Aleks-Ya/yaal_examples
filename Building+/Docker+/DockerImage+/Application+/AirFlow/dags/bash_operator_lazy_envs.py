"""BashOperator with lazy environment variables"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'bash_operator_lazy_env',
    default_args=default_args)

class LazyDict(dict):
    def __init__(self, lambda_dict: dict):
        super().__init__(lambda_dict)

    def items(self):
        return [(k, self.get(k)()) for k in self.keys()]


def get_title():
    print("Get title")
    return 'Mr'


origin_lambda_dict={'TITLE': lambda: get_title()}
env_lazy_dict = LazyDict(origin_lambda_dict)


the_task = BashOperator(
    task_id='the_task',
    bash_command='echo Title is ${TITLE}! ',
    env=env_lazy_dict,
    dag=dag)
