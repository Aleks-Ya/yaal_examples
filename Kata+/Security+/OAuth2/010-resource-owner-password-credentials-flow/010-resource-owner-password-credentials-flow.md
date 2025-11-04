# 010-resource-owner-password-credentials-flow

## Task
Get an Access Token by the "Resource Owner Password Credentials" flow.

## Steps
1. Prepare Keycloak Admin CLI:
     1. Run Keycloak: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
     2. Get access to `kcadm.sh`: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
     3. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
2. Create a client: `kcadm.sh create clients -s clientId=direct-access-client-1 -s secret=dac1 -s directAccessGrantsEnabled=true`
3. Create a user:
     1. Create a user: `kcadm.sh create users -s username=direct-access-user-1 -s enabled=true`
     2. Set password: `kcadm.sh set-password --username direct-access-user-1 -p dau1`
4. Request an access token:
```sh
curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token \
     -H 'Content-Type: application/x-www-form-urlencoded' \
     -d 'grant_type=password' \
     -d 'client_id=direct-access-client-1' \
     -d 'client_secret=dac1' \
     -d 'username=direct-access-user-1' \
     -d 'password=dau1'
```
5. Response example:
```json
{
    "access_token": "...",
    "refresh_token": "...",
    "expires_in": 60,
    "refresh_expires_in": 1800,
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "fba25744-44f0-4710-a10e-8d080725b9c2",
    "scope": "profile email"
}
````

## Cleanup
1. Stop Keycloak: `docker stop keycloak`
