"""
Using sub DAGs.
API: https://airflow.apache.org/code.html?highlight=branchpythonoperator#airflow.models.DAG
Doc: https://airflow.incubator.apache.org/concepts.html#subdags
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime
from airflow.models import DAG
from airflow.operators.dummy_operator import DummyOperator
from airflow.operators.subdag_operator import SubDagOperator

MAIN_DAG_NAME = 'sub_dag'

default_args = {
    'start_date': datetime.now()
}

main_dag = DAG(
    MAIN_DAG_NAME,
    default_args=default_args)

before_sub_dag_task = BashOperator(
    task_id='before_sub_dag_task',
    bash_command='echo BEFORE SUB DAG TASK',
    dag=main_dag)

after_sub_dag_task = BashOperator(
    task_id='after_sub_dag_task',
    bash_command='echo AFTER SUB DAG TASK',
    dag=main_dag)


def hard_coded_sub_dag(parent_dag_name, child_dag_name, start_date, schedule_interval):
    dag = DAG(
        '%s.%s' % (parent_dag_name, child_dag_name),
        schedule_interval=schedule_interval,
        default_args=default_args
    )
    dummy_task_1 = DummyOperator(
        task_id='dummy_task_1',
        dag=dag,
    )
    dummy_task_2 = DummyOperator(
        task_id='dummy_task_2',
        dag=dag,
    )
    dummy_task_1 >> dummy_task_2
    return dag

HARD_CODED_DAG_NAME = 'hard_coded_sub_dag_task'

hard_coded_sub_dag_task = SubDagOperator(
    subdag=hard_coded_sub_dag(MAIN_DAG_NAME, HARD_CODED_DAG_NAME,
                    main_dag.start_date, main_dag.schedule_interval),
    task_id=HARD_CODED_DAG_NAME,
    dag=main_dag,
)

def generated_sub_dag(parent_dag_name, child_dag_name, start_date, schedule_interval):
    dag = DAG(
        '%s.%s' % (parent_dag_name, child_dag_name),
        schedule_interval=schedule_interval,
        default_args=default_args
    )
    task_count = 3
    previous_task = None
    for i in range(task_count):
        task = DummyOperator(
            task_id='generated_task_' + str(i),
            dag=dag,
        )
        if previous_task:
            task.set_upstream(previous_task)
        previous_task = task
    return dag

GENERATED_CODED_DAG_NAME = 'generated_sub_dag_task'

generated_sub_dag_task = SubDagOperator(
    subdag=generated_sub_dag(MAIN_DAG_NAME, GENERATED_CODED_DAG_NAME,
                    main_dag.start_date, main_dag.schedule_interval),
    task_id=GENERATED_CODED_DAG_NAME,
    dag=main_dag,
)

before_sub_dag_task >> hard_coded_sub_dag_task  \
>> generated_sub_dag_task >> after_sub_dag_task
