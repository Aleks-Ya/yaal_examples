# 020-push-image-user

## Task
Push an Image to a ECR Repository as an IAM User.

## Steps
1. Open 2 terminals: for Root User and for IAM User
2. Set environment variables (both termnals)
	```shell
	export USER=kata-user-push-image-user
	export POLICY_NAME=kata-policy-push-image-user
	export POLICY_ARN=arn:aws:iam::523633434047:policy/$POLICY_NAME
	export REGISTRY=523633434047.dkr.ecr.us-east-1.amazonaws.com
	export NAMESPACE=kata-ns-push-image-root
	export REPOSITORY=kata-repo-push-image-root
	export TAG=v1
	```
3. Create a user (as root)
	1. Create a User
		1. Create a user: `aws iam create-user --user-name $USER`
		2. Create an access key: `aws iam create-access-key --user-name $USER`
		3. Authenticate CLI (as user):
			1. `export AWS_ACCESS_KEY_ID=`
			2. `export AWS_SECRET_ACCESS_KEY=`
			3. Test: `aws sts get-caller-identity`
	2. Assign permissions
		1. Create policy: `aws iam create-policy --policy-name $POLICY_NAME --policy-document file://user-policy.json`
		2. Attach policy: `aws iam attach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
4. Create a Repository (as user): `aws ecr create-repository --repository-name $NAMESPACE/$REPOSITORY`
5. Push an Image (as user)
	1. Authenticate Docker client: `aws ecr get-login-password | docker login --username AWS --password-stdin $REGISTRY`
	2. Pull an Image from DockerHub: `docker pull hello-world`
	3. Mark the Image with a tag: `docker tag hello-world $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
	4. Push the Image to the Repository: `docker push $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
	5. List images in the Repository: `aws ecr list-images --repository-name $NAMESPACE/$REPOSITORY`
6. Pull an Image (as user)
	1. Delete the image locally: `docker rmi $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
	2. Pull and run the image: `docker run $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG` 

## Cleanup
1. Delete Repository (as user): `aws ecr delete-repository --force --repository-name $NAMESPACE/$REPOSITORY`
2. Delete Image locally (as user): `docker rmi $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
3. Delete User (as root)
	1. Delete access key: `aws iam delete-access-key --user-name $USER --access-key-id AKIAXT2X4DW77MUANLLN`	
	2. Delete policy: 
		1. Detach policy: `aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN`
		2. Delete policy: `aws iam delete-policy --policy-arn $POLICY_ARN`
	3. Delete User: `aws iam delete-user --user-name $USER`
4. Delete env variables (both): `unset USER POLICY_NAME POLICY_ARN REGISTRY NAMESPACE REPOSITORY TAG AWS_ACCESS_KEY_ID AWS_SECRET_ACCESS_KEY`

## History
- 2025-10-11 success
- 2025-10-12 success

## Optional
List policy versions: `aws iam list-policy-versions --policy-arn $POLICY_ARN`
Update policy: `aws iam create-policy-version --set-as-default --policy-arn $POLICY_ARN --policy-document file://user-policy.json`
Delete policy version: `aws iam delete-policy-version --policy-arn $POLICY_ARN --version-id v1`
