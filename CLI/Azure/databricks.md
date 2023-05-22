# databricks CLI

Docs: https://learn.microsoft.com/en-us/azure/databricks/dev-tools/cli/

Install: `pip install databricks-cli`

## Commands
### Info
Version: `databricks --version`
Help about a command group: `databricks fs -h`
Help about a subcommand: `databricks fs cp -h`

### Cluster
List clusters: `databricks clusters list`
Show cluster metadata (by cluster id): `databricks clusters get --cluster-id 0411-065519-9uuqxbma`
Show cluster metadata (by cluster name): `databricks clusters get --cluster-name my_test_databricks`

### Authentication
Set Databricks personal token: `databricks configure --token`
Check authentication: `databricks fs ls dbfs:/`

## Secrets
List secret scopes: `databricks secrets list-scopes`
Create a secret scope: `databricks secrets create-scope --scope my-scope`
List secrets in a scope: `databricks secrets list --scope my-scope`
Create a text-based secret: `databricks secrets put --scope my-scope --key my-secret --string-value my-value`

### Other
List files in a directory: `databricks fs ls dbfs:/cluster-logs-anna/driver`
Show file content: `databricks fs cat  dbfs:/cluster-logs-anna/driver/log4j-active.log`
