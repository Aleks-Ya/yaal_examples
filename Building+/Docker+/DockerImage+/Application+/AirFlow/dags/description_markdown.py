"""
Add a Markdown description to a DAG or a task.
The description is shown in “Graph View” for DAGs, “Task Details” for tasks.
Doc: https://airflow.readthedocs.io/en/latest/concepts.html#documentation-notes
"""
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'start_date': datetime.now()
}

dag = DAG(
    'description_markdown',
    default_args=default_args)
dag.doc_md = """
# Markdown hi
## Subheader
Here's a [url](www.airbnb.com)

My numbered list:

1. one
1. two

My bulleted list:

- first
- second
"""

the_task = BashOperator(
    task_id='the_task',
    bash_command='echo THE_TASK',
    dag=dag)
the_task.doc_md = """\
# Title
Here's a [url](www.airbnb.com)

My list:

1. one
1. two

My bulleted list:

- first
- second
"""
