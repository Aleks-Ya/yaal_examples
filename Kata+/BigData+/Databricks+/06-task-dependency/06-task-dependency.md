# 06-task-dependency

## Task
Create a Task dependent on another Task.

## Prerequisites
1. A Workspace is created.
2. Instance pool `pool-1` exists.

## Steps
1. Test Python script: `python3 task.py 9 3`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/06-task-dependency`
	2. Copy the Python script: `databricks fs cp --overwrite task.py dbfs:/tmp/06-task-dependency/task.py`
3. Create a job: `databricks jobs create --json @job.json`
4. Run job

## Cleanup
1. Delete the job
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/06-task-dependency`
