# airflow CLI

[Docs](https://airflow.apache.org/cli.html)

Info:
Show help:  airflow -h
Show AirFlow version: airflow version

DAG:
Show all DAGs: airflow list_dags
Show tasks in a DAG: airflow list_tasks dag-id
Run a DAG: airflow trigger_dag dag-id
Run a DAG with payload: airflow trigger_dag --conf '{"message": "hi"}' dag-id

Variables:
Show all variables: airflow variables
Get value of a variable: airflow variables -g my_var
Delete a variable: airflow variables -x my_var
Import from JSON: airflow variables --import variables.json