# 010-push-image-root

## Task
Status: success
Push an Image to a ECR $NAMESPACE as the Root User.

## Setup
1. Set environment variables
```shell
export REGISTRY=523633434047.dkr.ecr.us-east-1.amazonaws.com
export NAMESPACE=kata-ns-push-image-root
export REPOSITORY=kata-repo-push-image-root
export TAG=v1
```
2. Create a Repository: `aws ecr create-repository --repository-name $NAMESPACE/$REPOSITORY`
3. Push an Image
	1. Authenticate Docker client: `aws ecr get-login-password | docker login --username AWS --password-stdin $REGISTRY`
	2. Pull an Image from DockerHub: `docker pull hello-world`
	3. Mark the Image with a tag: `docker tag hello-world $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
	4. Push the Image to the Repository: `docker push $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
	5. List images in the Repository: `aws ecr list-images --repository-name $NAMESPACE/$REPOSITORY`
4. Pull an Image
	1. Delete the image locally: `docker rmi $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
	2. Pull and run the image: `docker run $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG` 

## Cleanup
1. Delete Repository: `aws ecr delete-repository --force --repository-name $NAMESPACE/$REPOSITORY`
2. Delete Image locally: `docker rmi $REGISTRY/$NAMESPACE/$REPOSITORY:$TAG`
3. Unset env vars: `unset REGISTRY NAMESPACE REPOSITORY TAG`

## History
- 2025-10-12 success
