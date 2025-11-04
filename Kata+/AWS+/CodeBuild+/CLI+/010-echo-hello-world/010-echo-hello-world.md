# 010-echo-hello-world

## Task
Create a Build Project which prints "Hello World".

## Steps
1. Create Service Role
	1. Create Role: `aws iam create-role --role-name kata-role-echo-hello-world --assume-role-policy-document file://trust-policy.json`
	2. Create a Policy: 
	```shell
	aws iam put-role-policy \
		--role-name kata-role-echo-hello-world \
		--policy-name CodeBuild \
		--policy-document file://permissions-policy.json
	```
2. Create Build Project: `aws codebuild create-project --cli-input-json file://project.json`
3. Start a build: `aws codebuild start-build --project-name kata-project-echo-hello-world`
4. Check build status: `aws codebuild batch-get-builds --ids kata-project-echo-hello-world:2525a87f-88ce-41c2-bc20-6ca99fc4af8e`

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name kata-project-echo-hello-world`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name kata-role-echo-hello-world --policy-name CodeBuild`
	2. Delete Role: `aws iam delete-role --role-name kata-role-echo-hello-world`

## History
