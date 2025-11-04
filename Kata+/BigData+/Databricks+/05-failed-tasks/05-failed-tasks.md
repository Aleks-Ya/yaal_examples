# 05-failed-tasks

## Task
Handle failed Tasks in a Job.

## Prerequisites
1. A Workspace is created.
2. Instance pool `pool-1` exists.

## Steps
1. Test Python script:
	1. Successful task: `python3 divide.py 9 3`
	2. Failed task: `python3 divide.py 9 0`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/05-failed-tasks`
	2. Copy the Python script: `databricks fs cp --overwrite divide.py dbfs:/tmp/05-failed-tasks/divide.py`
3. Create a job: `databricks jobs create --json @job.json`
4. Run job

## Cleanup
1. Delete the job
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/05-failed-tasks`
