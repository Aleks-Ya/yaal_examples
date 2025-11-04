# 010-echo-hello-world

## Task
Create a Build Project which prints "Hello World".

## Steps
1. Change current dir
2. Set env vars
	```shell
	set -x
	export ROLE=kata-role-echo-hello-world
	export PROJECT=kata-project-echo-hello-world
	export POLICY=CodeBuild
	```
3. Create Service Role
	1. Create Role: `aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Create an inline Policy: 
	```shell
	aws iam put-role-policy --role-name $ROLE --policy-name $POLICY --policy-document file://permissions-policy.json
	```
4. Create Build Project: `aws codebuild create-project --cli-input-json file://project.json`
5. Start a build: `aws codebuild start-build --project-name $PROJECT`
6. Get build ID: `export BUILD_ID=$(aws codebuild list-builds-for-project --project-name $PROJECT --query 'ids[0]' --output text)`
7. Check the build status (need `SUCCEEDED`): `aws codebuild batch-get-builds --ids $BUILD_ID`

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name $PROJECT`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name $ROLE --policy-name $POLICY`
	2. Delete Role: `aws iam delete-role --role-name $ROLE`
3. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/$PROJECT`
4. Delete env variables: `set +x; unset ROLE PROJECT POLICY BUILD_ID`

## History
- 2025-11-04 success
