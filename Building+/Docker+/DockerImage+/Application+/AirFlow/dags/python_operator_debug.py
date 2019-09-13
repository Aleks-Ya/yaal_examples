"""Debug PythonOperator by overriding"""
from datetime import datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from airflow.utils.decorators import apply_defaults

default_args = {
    'start_date': datetime.now()
}

dag = DAG('python_operator_debug',
          default_args=default_args)


def print_date():
    print("Current date: ", datetime.now())


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


the_task = DebugPythonOperator(
    task_id='the_task',
    python_callable=print_date,
    dag=dag)
