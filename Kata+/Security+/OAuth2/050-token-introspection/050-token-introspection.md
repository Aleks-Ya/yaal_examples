# 050-token-introspection

## Task
Verify an Access and Refresh Tokens using the Introspection endpoint.

## Steps
1. Prepare Keycloak Admin CLI:
     1. Run Keycloak: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
     2. Get access to `kcadm.sh`: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
     3. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
2. Get an Access token and a Refresh token
	1. Create a client: `kcadm.sh create clients -s clientId=introspection-client-1 -s secret=ic1 -s directAccessGrantsEnabled=true`
	2. Create a user:
     	1. Create a user: `kcadm.sh create users -s username=introspection-user-1 -s enabled=true`
     	2. Set password: `kcadm.sh set-password --username introspection-user-1 -p iu1`
	3. Request an access token:
	```sh
	curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token \
     	-H 'Content-Type: application/x-www-form-urlencoded' \
     	-d 'grant_type=password' \
     	-d 'client_id=introspection-client-1' \
     	-d 'client_secret=ic1' \
     	-d 'username=introspection-user-1' \
     	-d 'password=iu1'
	```
3. Introspect the Access Token:
	1. Send request:
	```sh
	curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token/introspect \
  	-H "Content-Type: application/x-www-form-urlencoded" \
  	-d "client_id=introspection-client-1" \
  	-d "client_secret=ic1" \
  	-d "token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0WUkyS0F2YmdfbnpuSElqLWhLSkFPMEhZUExGWVpfdVhnWnNkZENjNXpRIn0.eyJleHAiOjE3MTUyOTg4NDYsImlhdCI6MTcxNTI5ODc4NiwianRpIjoiM2IzZGM5YjQtYzFiZi00YzllLTg5MjktZWE0YjI4YWNlNmQwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNDEyZjZkYzAtODM5Yy00OGExLWE0MmItMzVhZGUyMTYyOGI5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiaW50cm9zcGVjdGlvbi1jbGllbnQtMSIsInNlc3Npb25fc3RhdGUiOiJhZWM4MGYwYS02Nzg4LTQ2ZTktODdhNy1lNjFkMjY4ODU5NWUiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6ImFlYzgwZjBhLTY3ODgtNDZlOS04N2E3LWU2MWQyNjg4NTk1ZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoiaW50cm9zcGVjdGlvbi11c2VyLTEifQ.VBVoN2ZLqGYxMtK_Rnq7rJcAmOysOrk6LgHoZlktaf7vJU0fNSG9CKji5cc_KX421-kYRzppCpZ5iroF00BepTBCyaLrtfI1fFG-OeuuDfNT_e5x2C0W8sYA4FQYH5SEPwMXIA40GSrjiRCOE-_bhsz0sKzGqW5bghbbBJwKGE_gokyMp8BiqC6z1hAXSxWKVoo_zU3FoYjHNyVrBe6el-4tkUur7Iv4RQpqVSsHlj1wWG19Hv0ERvh1qu8MMDypftyfBDBbFn6KezBuqQ2gE2oCsz_8UL4SvyWCWmBZtZSGjiP8w9-2K2OkJ39EkaBenl-bBbPQWvMKFmNAUcd1lg"
	```
	2. Response example:
	```json
	{
	    "exp": 1715298846, 
	    "iat": 1715298786, 
	    "jti": "3b3dc9b4-c1bf-4c9e-8929-ea4b28ace6d0", 
	    "iss": "http://localhost:8080/realms/master", 
	    "aud": "account", 
	    "sub": "412f6dc0-839c-48a1-a42b-35ade21628b9", 
	    "typ": "Bearer", 
	    "azp": "introspection-client-1", 
	    "session_state": "aec80f0a-6788-46e9-87a7-e61d2688595e", 
	    "acr": "1", 
	    "realm_access": {"roles": ["default-roles-master", "offline_access", "uma_authorization"]}, 
	    "resource_access": {"account": {"roles": ["manage-account", "manage-account-links", "view-profile"]}}, 
	    "scope": "profile email", 
	    "sid": "aec80f0a-6788-46e9-87a7-e61d2688595e", 
	    "email_verified": false, 
	    "preferred_username": "introspection-user-1", 
	    "client_id": "introspection-client-1", 
	    "username": "introspection-user-1", 
	    "token_type": "Bearer", 
	    "active": true
	}
	```
4. Introspect the Refresh Token:
	1. Send request:
	```sh
	curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token/introspect \
  	-H "Content-Type: application/x-www-form-urlencoded" \
  	-d "client_id=introspection-client-1" \
  	-d "client_secret=ic1" \
  	-d "token=eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJmMzAyYjIyMy0zMTBmLTRhY2UtYWY0NS0yYmViOTZkMjIxYjQifQ.eyJleHAiOjE3MTUzMDAyNjUsImlhdCI6MTcxNTI5ODQ2NSwianRpIjoiMTAyOWZiMWQtODgxYS00NjZlLTkzZGYtNGQzMDVhZDVmZjk4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL21hc3RlciIsInN1YiI6IjQxMmY2ZGMwLTgzOWMtNDhhMS1hNDJiLTM1YWRlMjE2MjhiOSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJpbnRyb3NwZWN0aW9uLWNsaWVudC0xIiwic2Vzc2lvbl9zdGF0ZSI6IjdkYzBhN2UyLTRhM2MtNDljNi1iMjU5LWUwODFlYmQ5MzNhZCIsInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6IjdkYzBhN2UyLTRhM2MtNDljNi1iMjU5LWUwODFlYmQ5MzNhZCJ9.KY52JHQzG6yw39eXzWq2WFvTp5gVusQwyKFghLiMFjh5cjSVPts0SfTa305PS97vzT7z31_jrEIHEdJ3e3LZ7Q"
	```
	2. Response example:
	```json
	{
    	"exp": 1715300265, 
    	"iat": 1715298465, 
    	"jti": "1029fb1d-881a-466e-93df-4d305ad5ff98", 
    	"iss": "http://localhost:8080/realms/master", 
    	"aud": ["http://localhost:8080/realms/master", "account"], 
    	"sub": "412f6dc0-839c-48a1-a42b-35ade21628b9", 
    	"typ": "Refresh", 
    	"azp": "introspection-client-1", 
    	"session_state": "7dc0a7e2-4a3c-49c6-b259-e081ebd933ad", 
    	"acr": "1", 
    	"realm_access": {"roles": ["default-roles-master", "offline_access", "uma_authorization"] }, 
    	"resource_access": {"account": {"roles": ["manage-account", "manage-account-links", "view-profile"]}}, 
    	"scope": "profile email", 
    	"sid": "7dc0a7e2-4a3c-49c6-b259-e081ebd933ad", 
    	"email_verified": false, 
    	"preferred_username": "introspection-user-1", 
    	"client_id": "introspection-client-1", 
    	"username": "introspection-user-1", 
    	"token_type": "Refresh", 
    	"active": true
	}
	```

## Cleanup
1. Stop Keycloak: `docker stop keycloak`
