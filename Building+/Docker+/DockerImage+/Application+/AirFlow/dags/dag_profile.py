"""Profiles in DAG"""
import os
from datetime import datetime

from airflow import DAG
from airflow.models import Variable
from airflow.operators.python_operator import PythonOperator


class DagProfile:
    def __init__(self, profile=None):
        self.profile = profile
        print(f"DagProfile with profile '{profile}' is created.")

    def get_profile(self) -> str:
        return self.profile

    @staticmethod
    def __get_variable_safe(name: str):
        try:
            return Variable.get(name)
        except KeyError:
            return None

    def get_variable(self, name: str, is_mandatory=False, default=None) -> str:
        name_with_profile = f"{name}_{self.profile}"
        print(f"Name with profile variable: '{name_with_profile}'")
        value = self.__get_variable_safe(name_with_profile)
        print(f"Name with profile variable value: '{value}'")
        if value is None:
            value = self.__get_variable_safe(name)
            print(f"Name variable value: '{value}'")
        print(f"Final variable value: '{value}'")
        return value

    def __get_file_name_with_profile(self, file: str) -> str:
        split = os.path.splitext(file)
        file_name = split[0]
        file_ext = split[1]
        file_with_profile = f"{file_name}-{self.profile}{file_ext}"
        return file_with_profile

    def get_file(self, file: str):
        file_with_profile = self.__get_file_name_with_profile(file)
        return file_with_profile if os.path.isfile(file_with_profile) else file


default_args = {
    'start_date': datetime.now()
}


def print_info(**context):
    profile: str = context['params']['profile']
    print(f"Profile from context: {profile}")
    dag_profile: DagProfile = DagProfile(profile)
    print(f"Profile in DagProfile: {dag_profile.get_profile()}")
    file: str = dag_profile.get_file('/my/file.txt')
    print(f"file: {file}")
    var_name = 'dag_profile_data'
    var_value: str = dag_profile.get_variable(var_name)
    print(f"variable '{var_name}': '{var_value}'")


dag_main = DAG(
    'dag_profile_main',
    params={'profile': None},
    default_args=default_args)

main_dag_task = PythonOperator(
    task_id='main_dag_task',
    python_callable=print_info,
    provide_context=True,
    dag=dag_main)

dag_stage = DAG(
    'dag_profile_stage',
    params={'profile': 'stage'},
    default_args=default_args)

stage_dag_task = PythonOperator(
    task_id='stage_dag_task',
    python_callable=print_info,
    provide_context=True,
    dag=dag_stage)
