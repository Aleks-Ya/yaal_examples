# 08-if-else-condition-task

## Task
Use Task of type "if/else condition".

## Prerequisites
1. A Workspace is created.
2. All-purpose Cluster `cluster-1` exists.

## Steps
1. Test Python scripts:
	1. Divide: `python3 divide.py 9 3`
	2. Set task values: `python3 set_task_values.py lucky=7`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/08-if-else-condition-task`
	2. Copy the Python script: `databricks fs cp --overwrite divide.py dbfs:/tmp/08-if-else-condition-task/divide.py`
	3. Copy the Python script: `databricks fs cp --overwrite set_task_values.py dbfs:/tmp/08-if-else-condition-task/set_task_values.py`
3. Create jobs:
	1. Create "Task values" job: `databricks jobs create --json @task-values-job.json`
4. Run jobs

## Cleanup
1. Delete the jobs
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/08-if-else-condition-task`
