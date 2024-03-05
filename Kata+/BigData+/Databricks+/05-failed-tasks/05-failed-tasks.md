# 05-failed-tasks

## Task
Handle failed Tasks in a Job.

## Prerequisites
1. A Workspace is created.
2. Instance pool `pool-1` exists.

## Setup
1. Test Python script:
	1. Successful task: `python3 task.py 9 3`
	2. Failed task: `python3 task.py 9 0`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/05-failed-tasks`
	2. Copy the Python script: `databricks fs cp --overwrite task.py dbfs:/tmp/05-failed-tasks/task.py`
3. Create a job: `databricks jobs create --json @job.json`
4. Run job

## Cleanup
1. Delete the job
2. Delte Python files: `databricks fs rm dbfs:/tmp/05-failed-tasks/task.py`
