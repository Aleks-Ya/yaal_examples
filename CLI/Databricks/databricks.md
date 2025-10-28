# databricks CLI
 
Docs: https://learn.microsoft.com/en-us/azure/databricks/dev-tools/cli/
GitHub: https://github.com/databricks/cli
 
## Install
Determine the latest version: https://github.com/databricks/cli/releases
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
Get cluster ID by cluster name (first): `databricks clusters list --output JSON | jq -r 'first(.[] | select(.cluster_name == "cluster-1") | .cluster_id)'`
Create cluster for JSON definition (cluster starts immediately): `databricks clusters create --json @databricks_cluster_create.json`
 
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
Download folder from DBFS: `databricks fs cp -r dbfs:/remote/directory /local/out` (e.g. `directory/a.txt` -> `out/a.txt`)
Delete a file: `databricks fs rm dbfs:/remote/directory/file.txt`
Delete a directory: `databricks fs rm -r dbfs:/dir1`
List files in a directory: `databricks fs ls dbfs:/cluster-logs-anna/driver`
Show file content: `databricks fs cat dbfs:/cluster-logs-anna/driver/log4j-active.log`
Copy a directory: `databricks fs cp -r dbfs:/src_dir dbfs:/dest_dir` (`src_dir/*` copied to `dest_dir/*`)
Move a dirctory: copy + delete
 
#### Workspace (`/Workspace` dir is hidden in DBFS)
List files in `/Workspace` dir: `databricks workspace list /`
Create a directory: `databricks workspace mkdirs /dir1`
Upload a Python file as a Notebook into Workspace: `databricks workspace import --file script.py /tmp`
Upload a directory to Workspace: `databricks workspace import-dir --overwrite /tmp/local-dir /MyWorkspace/destination/`
Delete object in a Workspace: `databricks workspace delete /initScripts/init.py`
Deleta a folder in a Workspace: `databricks workspace delete --recursive /MyWorkspace/dir1`
 
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
Create job from JSON file (pipe, DO NOT WORK!!!!): `cat my_job.json | databricks jobs create --json -`
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

### Instance Pool
List instance pools: `databricks instance-pools list`
Show instance pool details: `databricks instance-pools get 0226-061058-pol22-pool-igncb5ea`
Create an Instance Pool from JSON definition (file): `databricks instance-pools create --json @instance_pool.json`
Get Instance Pool ID by name (first): `databricks instance-pools list --output JSON | jq -r 'first(.[] | select(.instance_pool_name == "pool-1") | .instance_pool_id)'`

### Permissions
List all job permission levels: `databricks jobs get-permission-levels 122862247468184`
Get job permissions: `databricks jobs get-permissions 122862247468184`
Set job permissions: `databricks jobs set-permissions 122862247468184 --json ???`
Update job permissions: `databricks jobs update-permissions 122862247468184 --json {\"access_control_list\":[{\"group_name\":\"CdipSearchAuthors\",\"permission_level\":\"CAN_MANAGE\"}]}`

### Unity Catalog
List catalogs: `databricks catalogs list`
List schemas in a catalog: `databricks schemas list my-catalog` 
List tables in a schema: `databricks table list my-catalog my-schema`

### Volumes
List volumes: `databricks volumes list schema1 catalog1`
Show details: `databricks volumes read shema1.catalog1.volume1`
Create a volume: `databricks volumes create schema1 catalog1 volume1 MANAGED`
List files in a volume: `databricks fs ls dbfs:/Volumes/schema1/catalog1/volume1`
