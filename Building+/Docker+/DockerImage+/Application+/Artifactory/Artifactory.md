# JFrog Artifactory

## Info
https://jfrog.com/help/r/jfrog-installation-setup-documentation/docker
https://jfrog.com/help/r/jfrog-installation-setup-documentation/database-configuration


Pull image: `docker pull releases-docker.jfrog.io/jfrog/artifactory-oss`

Run:
```shell
docker run --rm -it --name artifactory \
	-p 8081:8081 \
	-e JF_SHARED_DATABASE_ALLOWNONPOSTGRESQL=true \
	docker.bintray.io/jfrog/artifactory-oss:latest
```


	-e JF_SHARED_NODE_IP=172.17.0.1 \
