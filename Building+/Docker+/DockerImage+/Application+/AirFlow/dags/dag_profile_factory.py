"""Generate base dag and profile dags by DagFactory"""
from datetime import datetime
from typing import List, Dict, Callable

from airflow import DAG
from airflow.models import BaseOperator
from airflow.operators.python_operator import PythonOperator


class DagFactory:
    def __init__(self,
                 dag_id: str,
                 profiles: List[str] = None,
                 params: Dict[str, object] = None,
                 default_args: Dict[str, object] = None):
        main_dag = DAG(dag_id, params=params, default_args=default_args)
        self.dags: List[DAG] = [main_dag]
        if profiles is not None:
            for profile in profiles:
                profile_dag_name = f"{dag_id}_{profile}"
                profile_dag = DAG(profile_dag_name,
                                  params=params,
                                  default_args=default_args)
                self.dags.append(profile_dag)

    def add_operator(self, func: Callable[[DAG], BaseOperator]):
        for dag in self.dags:
            func(dag)
        return self

    def set_downstream(self, upstream_task_id: str, downstream_task_ids: List[str]):
        for dag in self.dags:
            task_dict = dag.task_dict
            upstream_task: BaseOperator = task_dict[upstream_task_id]
            downstream_tasks = [task_dict[downstream_task_id] for downstream_task_id in downstream_task_ids]
            upstream_task.set_downstream(downstream_tasks)
        return self

    def create_dags(self) -> None:
        for dag in self.dags:
            globals()[dag.dag_id] = dag


__my_task_1_id = 'my_python_operator_1'
__my_task_2_id = 'my_python_operator_2'
__my_task_3_id = 'python_operator_task'
DagFactory(
    dag_id='dag_profile_factory',
    profiles=['stage'],
    params={},
    default_args={
        'start_date': datetime.now()
    }
).add_operator(
    lambda dag: PythonOperator(task_id=__my_task_1_id,
                               python_callable=lambda: print('hi from my_python_operator_1'),
                               dag=dag)
).add_operator(
    lambda dag: PythonOperator(task_id=__my_task_2_id,
                               python_callable=lambda: print('hi from my_python_operator_2'),
                               dag=dag)
).add_operator(
    lambda dag: PythonOperator(task_id=__my_task_3_id,
                               python_callable=lambda: print('hi from my_python_operator_3'),
                               dag=dag)
).set_downstream(
    upstream_task_id=__my_task_1_id,
    downstream_task_ids=[__my_task_2_id]
).set_downstream(
    upstream_task_id=__my_task_2_id,
    downstream_task_ids=[__my_task_3_id]
).create_dags()
