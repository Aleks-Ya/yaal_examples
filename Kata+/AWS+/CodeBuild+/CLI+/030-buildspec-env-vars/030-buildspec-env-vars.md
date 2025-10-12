# 030-buildspec-env-vars

## Task
Status: succeed
Use environment variables in Build Spec.

## Setup
1. Change current dir
2. Set env vars
```shell
export BUCKET=kata-bucket-buildspec-env-vars
export PROJECT=kata-project-buildspec-env-vars
export ROLE=kata-role-buildspec-env-vars
```
3. Create an S3 Bucket
	1. Create Bucket: `aws s3 mb s3://$BUCKET`
	2. Upload file: `aws s3 cp buildspec.yml s3://$BUCKET/`
4. Create Service Role
	1. Create Role: `aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Create Policy: `aws iam put-role-policy --role-name $ROLE --policy-name CodeBuild --policy-document file://permissions-policy.json`
5. Create Build Project:
```shell
aws codebuild create-project \
	--name $PROJECT \
	--source type=S3,location=$BUCKET/ \
	--artifacts type=NO_ARTIFACTS \
	--environment "type=LINUX_CONTAINER,image=aws/codebuild/standard:5.0,computeType=BUILD_GENERAL1_SMALL,environmentVariables=[{name=ENV_VAR4,value=Johnny,type=PLAINTEXT}]" \
	--service-role arn:aws:iam::523633434047:role/$ROLE
```
6. Start tailing logs: 
```shell 
aws logs start-live-tail --log-group-identifiers arn:aws:logs:us-east-1:523633434047:log-group:/aws/codebuild/$PROJECT
```
7. Start a build: `aws codebuild start-build --project-name $PROJECT --environment-variables-override name=ENV_VAR0,value=Jimmy,type=PLAINTEXT`
8. Check build status: `aws codebuild batch-get-builds --ids $PROJECT:1c388abe-5c47-4081-810b-1ed53fe5c826`
9. Find `Hello World` in the log tail

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name $PROJECT`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name $ROLE --policy-name CodeBuild`
	2. Delete Role: `aws iam delete-role --role-name $ROLE`
3. Delete Bucket: `aws s3 rb --force s3://$BUCKET`
4. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/$PROJECT`
5. Delete env variables: `unset BUCKET PROJECT ROLE`

## History
- 2025-10-11 succeed
- 2025-10-12 succeed
