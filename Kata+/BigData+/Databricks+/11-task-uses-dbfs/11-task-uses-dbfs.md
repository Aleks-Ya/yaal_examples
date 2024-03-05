# 11-task-uses-dbfs

## Task
Work with DBFS from a Task.

## Prerequisites
1. A Workspace is created.
2. All-purpose Cluster `cluster-1` exists.

## Setup
1. Upload Python scripts:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/11-task-uses-dbfs`
	2. Copy the Python script: `databricks fs cp --overwrite dbfs.py dbfs:/tmp/11-task-uses-dbfs/dbfs.py`
2. Create jobs:
	1. Create "At least one succeeded" job: `databricks jobs create --json @job.json`
3. Run jobs

## Cleanup
1. Delete the jobs
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/11-task-uses-dbfs`
