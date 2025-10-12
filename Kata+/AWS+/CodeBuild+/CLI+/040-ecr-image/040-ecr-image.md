# 040-ecr-image

## Task
Status: success
Create a Build Project which builds an ECR Docker Image.

## Setup
1. Change current dir
2. Set env vars
```shell
export BUCKET=kata-bucket-ecr-image
export PROJECT=kata-project-ecr-image
export ROLE=kata-role-ecr-image
export REGISTRY=523633434047.dkr.ecr.us-east-1.amazonaws.com
export NAMESPACE=kata-ns-ecr-image
export REPOSITORY=kata-repo-ecr-image
export TAG=v1
```
3. Create an ECR Repository: `aws ecr create-repository --repository-name $NAMESPACE/$REPOSITORY`
4. Create an S3 Bucket
	1. Create Bucket: `aws s3 mb s3://$BUCKET`
	2. Upload files: 
	```shell
	aws s3 cp Dockerfile s3://$BUCKET/
	aws s3 cp buildspec.yml s3://$BUCKET/
	```
5. Create Service Role
	1. Create Role: `aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Create Policy: `aws iam put-role-policy --role-name $ROLE --policy-name CodeBuild --policy-document file://permissions-policy.json`
6. Create Build Project: 
```shell
aws codebuild create-project \
	--name $PROJECT \
	--source type=S3,location=$BUCKET/ \
	--artifacts type=NO_ARTIFACTS \
	--environment type=LINUX_CONTAINER,image=aws/codebuild/standard:5.0,computeType=BUILD_GENERAL1_SMALL \
	--service-role arn:aws:iam::523633434047:role/$ROLE
```
7. Start tailing logs:
```shell 
aws logs start-live-tail --log-group-identifiers arn:aws:logs:us-east-1:523633434047:log-group:/aws/codebuild/$PROJECT
```
8. Start a build: `aws codebuild start-build --project-name $PROJECT`
9. Check build status: `aws codebuild batch-get-builds --ids $PROJECT:23380034-48f0-442e-b7f1-1d98ee4e515a`
10. Verify 
	1. List images in the Repository: `aws ecr list-images --repository-name $NAMESPACE/$REPOSITORY`
	2. Run image locally:
		1. Authenticate Docker client: `aws ecr get-login-password | docker login --username AWS --password-stdin $REGISTRY`
		2. Pull and run the image: `docker run $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`

## Cleanup
1. Delete Build Project: `aws codebuild delete-project --name $PROJECT`
2. Delete Service Role: 
	1. Delete Policy: `aws iam delete-role-policy --role-name $ROLE --policy-name CodeBuild`
	2. Delete Role: `aws iam delete-role --role-name $ROLE`
3. Delete Repository: `aws ecr delete-repository --force --repository-name $NAMESPACE/$REPOSITORY`
4. Delete Bucket: `aws s3 rb --force s3://$BUCKET`
5. Delete Log Group: `aws logs delete-log-group --log-group-name /aws/codebuild/$PROJECT`
6. Delete env variables: `unset BUCKET PROJECT ROLE REGISTRY NAMESPACE REPOSITORY TAG`

## History
- 2025-10-12 success
