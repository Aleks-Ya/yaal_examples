# 040-ecr-image

## Task
Status: not finished
Create a Build Project which builds an ECR Docker Image.

## Setup
1. Create an ECR Repository: `aws ecr create-repository --repository-name kata-repo-ecr-image/hello-ecr`
2. Create an S3 Bucket
	1. Create Bucket: `aws s3 mb s3://kata-bucket-ecr-image`
	2. Upload files: 
	```shell
	aws s3 cp Dockerfile s3://kata-bucket-ecr-image/
	aws s3 cp buildspec.yml s3://kata-bucket-ecr-image/
	```
3. Create Service Role
	1. Create Role: `aws iam create-role --role-name kata-role-ecr-image --assume-role-policy-document file://trust-policy.json`
	2. Create a Policy: 
	```shell
	aws iam put-role-policy \
		--role-name kata-role-ecr-image \
		--policy-name CodeBuild \
		--policy-document file://permissions-policy.json
	```
4. Create Build Project: 
```shell
aws codebuild create-project \
	--name kata-project-ecr-image \
	--source type=S3,location=kata-bucket-ecr-image/ \
	--artifacts type=NO_ARTIFACTS \
	--environment type=LINUX_CONTAINER,image=aws/codebuild/standard:5.0,computeType=BUILD_GENERAL1_SMALL \
	--service-role arn:aws:iam::523633434047:role/kata-role-ecr-image
```
5. Start tailing logs:
```shell 
aws logs start-live-tail \
	--log-group-identifiers arn:aws:logs:us-east-1:523633434047:log-group:/aws/codebuild/kata-project-ecr-image
```
6. Start a build: `aws codebuild start-build --project-name kata-project-ecr-image`
7. Check build status: `aws codebuild batch-get-builds --ids kata-project-ecr-image:ebd93c40-dba6-4bae-9413-30570704fd96`
8. Verify 
	1. List images in the Repository: `aws ecr list-images --repository-name kata-repo-ecr-image/hello-ecr`
	2. Run image locally:
		1. Authenticate Docker client: 
		```shell
		aws ecr get-login-password --region us-east-1 | \
			docker login --username AWS --password-stdin 523633434047.dkr.ecr.us-east-1.amazonaws.com
		```
		2. Pull and run the image: `docker run 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-ecr-image/hello-ecr:v1`

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name kata-project-ecr-image`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name kata-role-ecr-image --policy-name CodeBuild`
	2. Delete Role: `aws iam delete-role --role-name kata-role-ecr-image`
3. Delete Repository: `aws ecr delete-repository --force --repository-name kata-repo-ecr-image/hello-ecr`
4. Delete Bucket: `aws s3 rb --force s3://kata-bucket-ecr-image`
5. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/kata-project-ecr-image`

## History
