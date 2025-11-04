# 030-implicit-grant-flow

## Task
Get an Access Token by the "Implicit Grant" flow.

## Steps
1. Prepare Keycloak Admin CLI:
     1. Run Keycloak: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
     2. Get access to `kcadm.sh`: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
     3. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
2. Create a client: `kcadm.sh create clients -s clientId=implicit-client-1 -s secret=ic1 -s implicitFlowEnabled=true -s redirectUris='["http://localhost:8089"]'`
3. Create a user:
     1. Create a user: `kcadm.sh create users -s username=implicit-user-1 -s enabled=true`
     2. Set password: `kcadm.sh set-password --username implicit-user-1 -p iu1`
4. Request an Access Token: 
	1. Start the redirect server: `while true; do echo -e "HTTP/1.1 200 OK\r\nContent-Length: 2\r\n\r\nOK" | nc -lN 8089; done`
    2. Open in a private tab browser: http://localhost:8080/realms/master/protocol/openid-connect/auth?response_type=token&client_id=implicit-client-1&redirect_uri=http%3A//localhost%3A8089&state=state1&scope=profile&nonce=nonce1
    3. Login: `implicit-user-1`/`iu1`
    4. See the Access Token in browser's URL
5. Browser's URL example:
```
http://localhost:8089/#state=state1
&access_token=...
&expires_in=900
&token_type=Bearer
&session_state=cc2016a2-7b7a-4d47-b5d1-9724b50c286d
&iss=http%3A%2F%2Flocalhost%3A8080%2Frealms%2Fmaster
```

## Cleanup
1. Stop Keycloak: `docker stop keycloak`
