# 02-setup-cli

## Task
Configure Databricks CLI to access to a Workspace.

## Setup
1. Create a Workspace
2. Open the Workspace
2. Configure Databricks CLI
	1. Create a token
		1. Choose "User Settings" -> "User" -> "Developer"
		2. Choose "Access tokens" -> "Manage"
		3. Click "Generate new token"
	2. Configure CLI
		1. Run `databricks configure --token6`
		2. Databricks host: Workspace URL from the browser, e.g. `dbc-0e774ec3-6004.cloud.databricks.com`
		3. Personal access token: generated access token
	3. Test CLI: `databricks clusters list`

## Cleanup
1. Delete Workspace in Databricks UI
2. Delete S3 buckets manually
	1. Emtpy the Bucket
	2. Delete the bucket
