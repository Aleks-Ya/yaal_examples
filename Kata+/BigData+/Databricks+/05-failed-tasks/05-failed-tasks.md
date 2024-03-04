# 05-failed-tasks

## Task
Handle failed Tasks in a Job.

## Prerequisites
1. Instance pool `pool-1` exists.

## Setup
1. Test Python scripts:
	1. Successful task: `python3 task_success.py`
	2. Failed task: `python3 task_failed.py`
1. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/05-failed-tasks`
	2. Copy the Python script: `databricks fs cp --overwrite task.py dbfs:/tmp/05-failed-tasks/task.py`
2. Create a job: `databricks jobs create --json @job.json`
3. Run job

## Cleanup
1. Delete the job
2. Delte Python files: `databricks fs rm dbfs:/tmp/05-failed-tasks/task.py`
