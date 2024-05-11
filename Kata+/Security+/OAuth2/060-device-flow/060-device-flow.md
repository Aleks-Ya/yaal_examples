# 060-device-flow

## Task
Get an Access Token by the "Device Authorization Grant" flow.

## Setup
1. Prepare Keycloak Admin CLI:
     1. Run Keycloak: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
     2. Get access to `kcadm.sh`: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
     3. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
2. Create a client: `kcadm.sh create clients -s clientId=device-client-1 -s secret=dc1 -s attributes='{"oauth2.device.authorization.grant.enabled":true}' -s redirectUris='["http://localhost:8089"]'`
3. Create a user:
     1. Create a user: `kcadm.sh create users -s username=device-user-1 -s enabled=true`
     2. Set password: `kcadm.sh set-password --username device-user-1 -p du1`
4. Get Device Code and User Code:
```sh
curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/auth/device \
     -H 'Content-Type: application/x-www-form-urlencoded' \
     -d 'client_id=device-client-1' \
     -d 'client_secret=dc1'
```
5. Verification by user
	1. Get `verification_uri_complete` from the response
	2. Open the URL in a private browser
	3. Login as `device-user-1`/`du1`
	4. Confirm access
6. Request an Access Token and a Refresh Token (replace `device_code`):
```sh
curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token \
     -H 'Content-Type: application/x-www-form-urlencoded' \
     -d 'grant_type=urn:ietf:params:oauth:grant-type:device_code' \
     -d 'client_id=device-client-1' \
     -d 'client_secret=dc1' \
     -d 'device_code=3r_x6gTqCKW04tcB652XIIekqcrqA7kR5Zmc_iayiYw'
```
Response example:
```json
{
    "access_token": "...",
    "refresh_token": "...",
    "expires_in": 60,
    "refresh_expires_in": 1800,
    "token_type": "Bearer",
    "not-before-policy": 0,
    "session_state": "8ddcafd0-85d8-447b-9114-35d3b574b98d",
    "scope": "email profile"
}
```

## Cleanup
1. Stop Keycloak: `docker stop keycloak`
