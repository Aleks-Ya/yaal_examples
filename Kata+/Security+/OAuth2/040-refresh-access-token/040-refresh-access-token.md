# 040-refresh-access-token

## Task
Get an Access Token using a Refresh Token.

## Setup
1. Prepare Keycloak Admin CLI:
     1. Run Keycloak: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
     2. Get access to `kcadm.sh`: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
     3. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
2. Get an Access token
	1. Create a client: `kcadm.sh create clients -s clientId=refresh-client-1 -s secret=rc1 -s directAccessGrantsEnabled=true`
	2. Create a user:
     	1. Create a user: `kcadm.sh create users -s username=refresh-user-1 -s enabled=true`
     	2. Set password: `kcadm.sh set-password --username refresh-user-1 -p ru1`
	3. Request an access token:
	```sh
	curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token \
     	-H 'Content-Type: application/x-www-form-urlencoded' \
     	-d 'grant_type=password' \
     	-d 'client_id=refresh-client-1' \
     	-d 'client_secret=rc1' \
     	-d 'username=refresh-user-1' \
     	-d 'password=ru1'
	```
5. Refresh the Access Token (replace `refresh_token`):
```sh
curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=refresh_token" \
  -d 'client_id=refresh-client-1' \
  -d 'client_secret=rc1' \
  -d "refresh_token=eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmMzAyYjIyMy0zMTBmLTRhY2UtYWY0NS0yYmViOTZkMjIxYjQifQ.eyJleHAiOjE3MTUyOTk4OTUsImlhdCI6MTcxNTI5ODA5NSwianRpIjoiNzc4OThjMTQtYmU1ZC00ZGViLTkxMWYtNzI4N2M4NDQzOTg5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL21hc3RlciIsInN1YiI6IjU1MGRjOGViLTdkOWQtNDJkNC1iYTJlLTdmODhkNzI5ZTE3MSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJyZWZyZXNoLWNsaWVudC0xIiwic2Vzc2lvbl9zdGF0ZSI6IjVlMTE5ZDNjLWFiZjEtNGRhZi05MzNjLTVjNDRlZmVlMDY2ZiIsInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6IjVlMTE5ZDNjLWFiZjEtNGRhZi05MzNjLTVjNDRlZmVlMDY2ZiJ9.56RyL8aD1xyre7d9QcO-OeozoSltjy9BPz9SVSmjVOk5F2VATaoHCup2_iS85mDfOomeGBF55kqrh1VGdfJyCw"
```
6. Response example:
```json
{
    "access_token": "...",
    "refresh_token": "...",
    "expires_in": 60,
    "refresh_expires_in": 1800,
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "5e119d3c-abf1-4daf-933c-5c44efee066f",
    "scope": "profile email"
}
```

## Cleanup
1. Stop Keycloak: `docker stop keycloak`
