# 01-create-workspace

## Task
Create a Databricks Workspace using Databricks UI.

## Setup
1. Open https://accounts.cloud.databricks.com/workspaces
2. Create workspace
	1. How would you like to create your workspace: `Quickstart`
	2. Workspace Name: `workspace-1`
	3. AWS Region of the Databricks workspace: `Oregon (us-west-2)`
	4. Click "Start Quickstart"
3. Quick create stack
	1. Databricks account password: my Databricks password
	2. Click "Create stack"
	3. Wait till creation finished
4. Test Workspace
	1. Open Workspace, login
	2. Create an All-purpose compute
		1. Name: `Alex Ya's Cluster`
		2. Nodes: `Single node`
		3. Use Photon Acceleration: `off`
		4. Node type: `m4.large`
		5. Terminate after minutes of inactivity: `20`
	3. Create a Notebook
		1. Name: `notebook-1`
		2. Cluster: `Alex Ya's Cluster`
		3. Cmd 1: `print('Hello, World!')`
		4. Run all

## Cleanup
1. Delete Workspace in Databricks UI
2. Emtpy the Workspace S3 Bucket
3. Delete Workspace CloudFormation Stack
