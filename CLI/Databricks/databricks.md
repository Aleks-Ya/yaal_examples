# databricks CLI
 
Docs: https://learn.microsoft.com/en-us/azure/databricks/dev-tools/cli/
 
## Install
Latest version: `brew tap databricks/tap && brew install databricks` (should be >= 0.212.4)
Outdated version: `pip install databricks-cli`
 
## Commands
### Info
Version: `databricks --version`
Help about a command group: `databricks fs -h`
Help about a subcommand: `databricks fs cp -h`
Debug log level: `databricks clusters list --debug`
 
### User
Current user details: `databricks current-user me`
 
### Cluster
List clusters: `databricks clusters list`
Show cluster metadata (by cluster id): `databricks clusters get --cluster-id 0411-065519-9uuqxbma`
Show cluster metadata (by cluster name): `databricks clusters get --cluster-name my_test_databricks`
Restart a cluster: `databricks clusters restart --cluster-id 0614-065657-yjnh9djz`
 
### Authentication
Set Databricks personal token: `databricks configure --token`
Check authentication: `databricks fs ls dbfs:/`
List profiles: `databricks auth profiles`
 
### File system
 
#### DBFS
List files in the root dir: `databricks fs ls dbfs:/`
Create a folder: `databricks fs mkdirs dbfs:/Users/aleksei.iablokov@sap.com`
Upload a file into DBFS (skip existing): `databricks fs cp /local/file.txt dbfs:/remote/directory`
Upload a file into DBFS (overwrite): `databricks fs cp --overwrite /local/file.txt dbfs:/remote/directory`
Download file from DBFS: `databricks fs cp dbfs:/remote/directory/file.txt /local/`
Delete a file: `databricks fs rm dbfs:/remote/directory/file.txt`
List files in a directory: `databricks fs ls dbfs:/cluster-logs-anna/driver`
Show file content: `databricks fs cat dbfs:/cluster-logs-anna/driver/log4j-active.log`
 
#### Workspace (`/Workspace` dir is hidden in DBFS)
List files in `/Workspace` dir: `databricks workspace list /`
 
### Secrets
List secret scopes: `databricks secrets list-scopes`
Create a secret scope: `databricks secrets create-scope --scope my-scope`
List secrets in a scope: `databricks secrets list --scope my-scope`
Create a text-based secret: `databricks secrets put --scope my-scope --key my-secret --string-value my-value`
List ACLs for a scope: `databricks secrets list-acls --scope my-scope`
Create an ACL for a scope: `databricks secrets put-acl --scope my-scope --principal aleksei.iablokov@sap.com --permission READ`
View ACLs in a scope: `databricks secrets list-acls --scope iablokov-tmp`
View principal's ACL: `databricks secrets get-acl --scope iablokov-tmp --principal aleksei.iablokov@sap.com`
 
### Groups
List all groups: `databricks groups list`
List users in a group: `databricks groups list-members --group-name admins`
 
### Libraries
List libraries in all clusters: `databricks libraries all-cluster-statuses`
List libraries in specific cluster
	- By cluster name: `databricks libraries cluster-status --cluster-name "aleksei.iablokov@sap.com's Cluster"`
	- By cluster id: `databricks libraries cluster-status --cluster-id 0614-065657-yjnh9djz`
Install a JAR library to a cluster: 
	- From ABFSS: `databricks libraries install --debug --cluster-id 0614-065657-yjnh9djz --jar "abfss://program-cic-rm-dev-rm-dev3@insightstoragedev.dfs.core.windows.net/databricks/jars/data-platform-spark-fake-1.0.jar"`
	- From DBFS: `databricks libraries install --debug --cluster-id 0614-065657-yjnh9djz --jar "dbfs:/Users/aleksei.iablokov@sap.com/data-platform-spark-fake-1.4.jar"`
Mark library for uninstall (need cluster restart): `databricks libraries uninstall --debug --cluster-id 0614-065657-yjnh9djz --jar "abfss://program-cic-rm-dev-rm-dev3@insightstoragedev.dfs.core.windows.net/databricks/jars/data-platform-spark-fake-1.0.jar"`
 
### Jobs
List all jobs (default limit 20): `databricks jobs list`
List all jobs (limit 100): `databricks jobs list --limit 10`
List jobs with name: `databricks jobs list --name my-job-name`
Show job details: `databricks jobs get --job-id 1234567890` (Job Id is displayed by the List jobs command)
Create job from JSON file: `databricks jobs create --json @my_job.json`
Create job from JSON file (Windows): `databricks jobs create --json @C:\Users\IABLOAL1\job.json`
Delete job (by Job ID): `databricks jobs delete 79957888534123`
 
### Job runs
List all job runs: `databricks runs list`
List job runs for a Job: `databricks runs list --job-id 1234567890`

### Instance profile
List instance profiles: `databricks instance-profiles list`

### Compute policy (Cluster policy)
List cluster policies: `databricks cluster-policies list`
Show cluster policy details: `databricks cluster-policies get 0014FDF3D7665C16`

### Policy family
List policy families: `databricks policy-families list`
Show policy family details: `databricks policy-families get personal-vm`