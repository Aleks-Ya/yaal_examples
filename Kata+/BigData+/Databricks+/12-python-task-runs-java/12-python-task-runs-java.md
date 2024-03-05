# 12-python-task-runs-java


Run Java Jar DOES NOT WORK

## Task
Execute `java` from a Python Script Task.

## Prerequisites
1. A Workspace is created.
2. All-purpose Cluster `cluster-1` exists.

## Setup
1. Test Python scripts:
	1. Divide: `python3 print_java_version.py`
2. Compile HelloWorld.jar:
	1. Choose Java 8: `sdk use java 8.0.402-zulu`
	2. Compile: `javac -d . HelloWorld.java`
	3. Create jar: `jar cvfe HelloWorld.jar hello.HelloWorld hello/HelloWorld.class`
	4. Test 1: `java -jar HelloWorld.jar`
	5. Test 2: `java -cp HelloWorld.jar hello.HelloWorld`
1. Upload files:
	1. Create a temp dir: `databricks fs mkdirs dbfs:/tmp/12-python-task-runs-java`
	2. Jar: `databricks fs cp --overwrite HelloWorld.jar dbfs:/tmp/12-python-task-runs-java/HelloWorld.jar`
	3. Print Java version: `databricks fs cp --overwrite print_java_version.py dbfs:/tmp/12-python-task-runs-java/print_java_version.py`
	4. Run Java Jar: `databricks fs cp --overwrite run_java_jar.py dbfs:/tmp/12-python-task-runs-java/run_java_jar.py`
2. Create jobs:
	1. Create "At least one succeededd" job: `databricks jobs create --json @job.json`
3. Run jobs

## Cleanup
1. Delete the jobs
2. Delte DBFS files: `databricks fs rm -r dbfs:/tmp/11-task-uses-dbfs`
