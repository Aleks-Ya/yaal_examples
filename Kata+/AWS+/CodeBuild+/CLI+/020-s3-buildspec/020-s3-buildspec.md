# 020-s3-buildspec

## Task
Create a Build Project from a BuildSpec located in S3 bucket.

## Steps
1. Create an S3 Bucket
	1. Create Bucket: `aws s3 mb s3://kata-bucket-s3-buildspec`
	2. Upload file: `aws s3 cp buildspec.yml s3://kata-bucket-s3-buildspec/`
2. Create Service Role
	1. Create Role: `aws iam create-role --role-name kata-role-s3-buildspec --assume-role-policy-document file://trust-policy.json`
	2. Create a Policy: 
	```shell
	aws iam put-role-policy \
		--role-name kata-role-s3-buildspec \
		--policy-name CodeBuild \
		--policy-document file://permissions-policy.json
	```
3. Create Build Project:
	```shell
	aws codebuild create-project \
		--name kata-project-s3-buildspec \
		--source type=S3,location=kata-bucket-s3-buildspec/ \
		--artifacts type=NO_ARTIFACTS \
		--environment type=LINUX_CONTAINER,image=aws/codebuild/standard:5.0,computeType=BUILD_GENERAL1_SMALL \
		--service-role arn:aws:iam::523633434047:role/kata-role-s3-buildspec
	```
4. Start a build: `aws codebuild start-build --project-name kata-project-s3-buildspec`
5. Check build status: `aws codebuild batch-get-builds --ids kata-project-s3-buildspec:2525a87f-88ce-41c2-bc20-6ca99fc4af8e`
6. Find `Hello World` in logs: 
	```shell
	aws logs get-log-events \
		--log-group-name /aws/codebuild/kata-project-s3-buildspec \
		--log-stream-name 42831698-e886-4e08-8b10-a8bcc86288d5
	```

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name kata-project-s3-buildspec`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name kata-role-s3-buildspec --policy-name CodeBuild`
	2. Delete Role: `aws iam delete-role --role-name kata-role-s3-buildspec`
3. Delete Bucket: `aws s3 rb --force s3://kata-bucket-s3-buildspec`
4. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/kata-project-s3-buildspec`

## History
