# DynamoDB Docker

DockerHub: https://hub.docker.com/r/amazon/dynamodb-local

Run: `docker run -p 8000:8000 amazon/dynamodb-local`

Configure local DynamoDB by default:
1. Option 1: `export AWS_ENDPOINT_URL_DYNAMODB=http://localhost:8000`
2. Option 2: configure `~/.aws/config`:
```
[default]
region = us-east-1
output = text
services = dynamodb1

[services dynamodb1]
dynamodb = 
  endpoint_url = http://localhost:8000
```
