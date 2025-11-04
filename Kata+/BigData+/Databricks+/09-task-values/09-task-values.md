# 09-task-values

## Task
Exchange variables between Tasks using Task Values.

## Prerequisites
1. A Workspace is created.
2. All-purpose Cluster `cluster-1` exists.

## Steps
1. Test Python script: `python3 divide.py 9 3`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/09-task-values`
	2. Copy the Python script: `databricks fs cp --overwrite set_task_values.py dbfs:/tmp/09-task-values/set_task_values.py`
	3. Copy the Python script: `databricks fs cp --overwrite print_task_values.py dbfs:/tmp/09-task-values/print_task_values.py`
3. Create a job: `databricks jobs create --json @job.json`
4. Run job

## Cleanup
1. Delete the job
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/09-task-values`
