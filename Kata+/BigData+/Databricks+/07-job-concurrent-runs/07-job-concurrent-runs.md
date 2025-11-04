# 07-task-concurrent-runs

## Task
Execute Tasks of a Job concurrently.

## Prerequisites
1. A Workspace is created.
2. Instance pool `pool-1` exists.

## Steps
1. Test Python script: `python3 sleep.py 3`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/07-job-concurrent-runs`
	2. Copy the Python script: `databricks fs cp --overwrite sleep.py dbfs:/tmp/07-job-concurrent-runs/sleep.py`
3. Create a job: `databricks jobs create --json @job.json`
4. Run job

## Cleanup
1. Delete the job
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/07-job-concurrent-runs`
