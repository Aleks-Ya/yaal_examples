# 020-authorization-code-flow

## Task
Get an Access Token by the "Authorization Code" flow.

## Steps
1. Prepare Keycloak Admin CLI:
	1. Run Keycloak: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
	2. Get access to `kcadm.sh`: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
	3. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
2. Create a client: `kcadm.sh create clients -s clientId=authorization-code-client-1 -s secret=acc1 -s standardFlowEnabled=true -s redirectUris='["http://localhost:8089"]'`
3. Create a user:
     1. Create a user: `kcadm.sh create users -s username=authorization-code-user-1 -s enabled=true`
     2. Set password: `kcadm.sh set-password --username authorization-code-user-1 -p acu1`
4. Request an Authorization Code: 
	1. Start the redirect server: `while true; do echo -e "HTTP/1.1 200 OK\r\nContent-Length: 2\r\n\r\nOK" | nc -lN 8089; done`
    2. Open in a private tab browser: http://localhost:8080/realms/master/protocol/openid-connect/auth?response_type=code&client_id=authorization-code-client-1&redirect_uri=http%3A//localhost%3A8089&state=state1&scope=profile
    3. Login: `authorization-code-user-1`/`acu1`
    4. See the Authorization Code in browser's URL or in `nc` logs
    5. Example of Browser's URL:
    ```
    http://localhost:8089/
    ?state=state1
    &session_state=ba46169e-1b39-42a3-bd54-a45d47ce48fc
    &iss=http%3A%2F%2Flocalhost%3A8080%2Frealms%2Fmaster
    &code=37e4deea-ae28-4c31-b48a-e5670772562e.ba46169e-1b39-42a3-bd54-a45d47ce48fc.4836304d-786b-4ace-a357-de488ae637df
    ```
5. Request an Access Token (replace `code`):
```sh
curl -X POST http://localhost:8080/realms/master/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code" \
  -d "code=9895dcc3-8b8d-481f-94d9-7cf6f92f73c3.ba46169e-1b39-42a3-bd54-a45d47ce48fc.4836304d-786b-4ace-a357-de488ae637df" \
  -d "redirect_uri=http%3A//localhost%3A8089" \
  -d "client_id=authorization-code-client-1" \
  -d "client_secret=acc1"
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
    "session_state": "ba46169e-1b39-42a3-bd54-a45d47ce48fc",
    "scope": "profile email"
}
```

## Cleanup
1. Stop Keycloak: `docker stop keycloak`
