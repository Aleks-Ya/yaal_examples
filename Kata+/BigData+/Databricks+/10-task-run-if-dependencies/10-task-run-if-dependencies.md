# 10-task-run-if-dependencies

## Task
Use Task setting "Run if dependencies".

## Prerequisites
1. A Workspace is created.
2. All-purpose Cluster `cluster-1` exists.

## Steps
1. Test Python scripts:
	1. Divide: `python3 divide.py 9 3`
2. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/10-task-run-if-dependencies`
	2. Copy the Python script: `databricks fs cp --overwrite divide.py dbfs:/tmp/10-task-run-if-dependencies/divide.py`
3. Create jobs:
	1. Create "At least one succeeded" job: `databricks jobs create --json @at-least-one-succeeded-job.json`
4. Run jobs

## Cleanup
1. Delete the jobs
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/10-task-run-if-dependencies`
