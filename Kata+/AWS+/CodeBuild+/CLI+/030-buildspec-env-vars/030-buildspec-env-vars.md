# 030-buildspec-env-vars

## Task
Status: succeed
Use environment variables in Build Spec.

## Setup
1. Create an S3 Bucket
	1. Create Bucket: `aws s3 mb s3://kata-bucket-buildspec-env-vars`
	2. Upload file: `aws s3 cp buildspec.yml s3://kata-bucket-buildspec-env-vars/`
2. Create Service Role
	1. Create Role: `aws iam create-role --role-name kata-role-buildspec-env-vars --assume-role-policy-document file://trust-policy.json`
	2. Create a Policy: 
	```shell
	aws iam put-role-policy \
		--role-name kata-role-buildspec-env-vars \
		--policy-name CodeBuild \
		--policy-document file://permissions-policy.json
	```
3. Create Build Project:
```shell
aws codebuild create-project \
	--name kata-project-buildspec-env-vars \
	--source type=S3,location=kata-bucket-buildspec-env-vars/ \
	--artifacts type=NO_ARTIFACTS \
	--environment type=LINUX_CONTAINER,image=aws/codebuild/standard:5.0,computeType=BUILD_GENERAL1_SMALL \
	--service-role arn:aws:iam::523633434047:role/kata-role-buildspec-env-vars
```
4. Start tailing logs: 
```shell 
aws logs start-live-tail \
	--log-group-identifiers arn:aws:logs:us-east-1:523633434047:log-group:/aws/codebuild/kata-project-buildspec-env-vars
```
4. Start a build: `aws codebuild start-build --project-name kata-project-buildspec-env-vars`
5. Check build status: `aws codebuild batch-get-builds --ids kata-project-buildspec-env-vars:1c388abe-5c47-4081-810b-1ed53fe5c826`
6. Find `Hello World` in the log tail

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name kata-project-buildspec-env-vars`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name kata-role-buildspec-env-vars --policy-name CodeBuild`
	2. Delete Role: `aws iam delete-role --role-name kata-role-buildspec-env-vars`
3. Delete Bucket: `aws s3 rb --force s3://kata-bucket-buildspec-env-vars`
4. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/kata-project-buildspec-env-vars`

## History
- 2025-10-11 succeed
