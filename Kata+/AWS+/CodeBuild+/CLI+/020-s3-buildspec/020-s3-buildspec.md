# 020-s3-buildspec

## Task
Create a Build Project from a BuildSpec located in S3 bucket.

## Steps
1. Change current dir
2. Set env vars
	```shell
	set -x
	export BUCKET=kata-bucket-s3-buildspec
	export ROLE=kata-role-s3-buildspec
	export POLICY=CodeBuild
	export PROJECT=kata-project-s3-buildspec
	```
3. Create an S3 Bucket
	1. Create Bucket: `aws s3 mb s3://$BUCKET`
	2. Upload file: `aws s3 cp buildspec.yml s3://$BUCKET/`
4. Create Service Role
	1. Create Role: `aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Create a Policy: 
	```shell
	aws iam put-role-policy --role-name $ROLE --policy-name $POLICY --policy-document file://permissions-policy.json
	```
5. Create Build Project:
	```shell
	aws codebuild create-project \
		--name $PROJECT \
		--source type=S3,location=$BUCKET/ \
		--artifacts type=NO_ARTIFACTS \
		--environment type=LINUX_CONTAINER,image=aws/codebuild/standard:5.0,computeType=BUILD_GENERAL1_SMALL \
		--service-role arn:aws:iam::523633434047:role/$ROLE
	```
6. Start a build: `aws codebuild start-build --project-name $PROJECT`
7. Get build ID: `export BUILD_ID=$(aws codebuild list-builds-for-project --project-name $PROJECT --query 'ids[0]' --output text)`
8. Check build status (need `SUCCEEDED`): `aws codebuild batch-get-builds --ids $BUILD_ID`
9. Find `Hello World` in logs: 
	```shell
	aws logs filter-log-events --log-group-name /aws/codebuild/$PROJECT --filter-pattern "Hello World"
	```

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name $PROJECT`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name $ROLE --policy-name $POLICY`
	2. Delete Role: `aws iam delete-role --role-name kata-role-s3-buildspec`
3. Delete Bucket: `aws s3 rb --force s3://$BUCKET`
4. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/$PROJECT`
5. Delete env variables: `set +x; unset BUCKET ROLE POLICY PROJECT BUILD_ID`

## History
- 2025-11-04 success
