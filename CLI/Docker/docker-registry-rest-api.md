Docker Registry REST API

Docs: [Docker Registry HTTP API V2](https://docs.docker.com/registry/spec/api/)

Get API version: /v2

Ping: GET /v2/_ping
GET /v2/_catalog
curl https://registry.hub.docker.com/v2/_catalog

Authentication:
curl -H 'Authorization:Basic aleks3490:pass'  https://registry.hub.docker.com/v2/_ping

DockerHub uses only V1 API 
Ping: curl https://registry.hub.docker.com/v1/_ping
Get image tags: curl https://registry.hub.docker.com/v1/repositories/debian/tags

---
