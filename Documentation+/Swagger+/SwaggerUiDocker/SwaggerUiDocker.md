# Run `swagger-ui` in Docker

## Docs
Installation: https://github.com/swagger-api/swagger-ui/blob/master/docs/usage/installation.md#docker
Configuration: https://github.com/swagger-api/swagger-ui/blob/master/docs/usage/configuration.md#docker

## Run
1. Run a container
	1. Show PetStore swagger.json: `docker run -p 80:8080 swaggerapi/swagger-ui`
	2. Show a local OpenAPI document: 
	`docker run -p 80:8080 -e SWAGGER_JSON=/swagger.json -v /tmp/local_swagger.json:/swagger.json swaggerapi/swagger-ui`
2. Open http://localhost
