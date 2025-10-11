# 020-push-image-user

## Task
Status: success
Push an Image to a ECR Repository as an IAM User.

## Setup
1. Create a user (as root)
	1. Create a User
		1. Create a user: `aws iam create-user --user-name kata-user-push-image-user`
		2. Create an access key: `aws iam create-access-key --user-name kata-user-push-image-user`
		3. Authenticate CLI:
			1. `export AWS_ACCESS_KEY_ID=`
			2. `export AWS_SECRET_ACCESS_KEY=`
			3. Test: `aws sts get-caller-identity`
	2. Assign permissions
		1. Create policy: `aws iam create-policy --policy-name kata-policy-push-image-user --policy-document file://user-policy.json`
		2. Attach policy: 
		```shell
		aws iam attach-user-policy \
			--user-name kata-user-push-image-user \
			--policy-arn arn:aws:iam::523633434047:policy/kata-policy-push-image-user
		```
2. Create a Repository: `aws ecr create-repository --repository-name kata-repo-push-image-user/hello-world`
3. Push an Image
	1. Authenticate Docker client: 
	```shell
	aws ecr get-login-password --region us-east-1 | \
		docker login --username AWS --password-stdin 523633434047.dkr.ecr.us-east-1.amazonaws.com
	```
	2. Pull an Image from DockerHub: `docker pull hello-world`
	3. Mark the Image with a tag: 
	```shell
	docker tag hello-world \
		523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-user/hello-world:v1
	```
	4. Push the Image to the Repository: 
	```shell
	docker push 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-user/hello-world:v1
	```
	5. List images in the Repository: `aws ecr list-images --repository-name kata-repo-push-image-user/hello-world`
4. Pull an Image
	1. Delete the image locally: `docker rmi 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-user/hello-world:v1`
	2. Pull and run the image: `docker run 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-user/hello-world:v1` 

## Cleanup
1. Delete Repository: `aws ecr delete-repository --force --repository-name kata-repo-push-image-user/hello-world`
2. Delete Image locally: `docker rmi 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-user/hello-world:v1`
3. Delete User (as root)
	1. Delete access key: `aws iam delete-access-key --user-name kata-user-push-image-user --access-key-id AKIAXT2X4DW75AX7OF6N`	
	2. Delete policy: 
		1. Detach policy: 
		```shell
		aws iam detach-user-policy \
			--user-name kata-user-push-image-user \
			--policy-arn arn:aws:iam::523633434047:policy/kata-policy-push-image-user
		```
		2. Delete policy: `aws iam delete-policy --policy-arn arn:aws:iam::523633434047:policy/kata-policy-push-image-user`
	3. Delete User: `aws iam delete-user --user-name kata-user-push-image-user`

## History
- 2025-10-11 success

## Optional
List policy versions: `aws iam list-policy-versions --policy-arn arn:aws:iam::523633434047:policy/kata-policy-push-image-user`
Update policy: 
```shell 
aws iam create-policy-version --set-as-default \
	--policy-arn arn:aws:iam::523633434047:policy/kata-policy-push-image-user \
	--policy-document file://user-policy.json
```
Delete policy version:
```shell
aws iam delete-policy-version --policy-arn arn:aws:iam::523633434047:policy/kata-policy-push-image-user --version-id v1
```
