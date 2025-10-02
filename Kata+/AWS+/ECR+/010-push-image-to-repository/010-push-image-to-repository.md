# 010-push-image-to-repository

## Task
Push an Image to a ECR Repository.

## Setup
1. Create a Repository: `aws ecr create-repository --repository-name kata-repo-push-image-to-repository/hello-world`
2. Push an Image
	1. Authenticate Docker client: 
	```shell
	aws ecr get-login-password --region us-east-1 | \
		docker login --username AWS --password-stdin 523633434047.dkr.ecr.us-east-1.amazonaws.com
	```
	2. Pull an Image from DockerHub: `docker pull hello-world`
	3. Mark the Image with a tag: 
	```shell
	docker tag hello-world \
		523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-to-repository/hello-world:v1
	```
	4. Push the Image to the Repository: 
	```shell
	docker push 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-to-repository/hello-world:v1
	```
	5. List images in the Repository: `aws ecr list-images --repository-name kata-repo-push-image-to-repository/hello-world`
3. Pull an Image
	1. Delete the image locally: `docker rmi 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-to-repository/hello-world:v1`
	2. Pull and run the image: `docker run 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-to-repository/hello-world:v1` 

## Cleanup
1. Delete Repository: `kata-repo-push-image-to-repository/hello-world`
2. Delete Image locally: `docker rmi 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-to-repository/hello-world:v1`
