# Authentication Server + Resource Server + OAuth Client

## Run Authentication Server

1. Run Keycloak by Docker: `Building+/Docker+/DockerImage+/Application+/Keycloak`
2. Create realm, client and user:
    1. Get an admin access token (expires in 60 sec):
       ```shell
       export ADMIN_TOKEN=$(
         curl -f -s -S \
           -H 'Content-Type: application/x-www-form-urlencoded' \
           -d 'client_id=admin-cli&username=admin&password=admin&grant_type=password' \
           http://localhost:8080/realms/master/protocol/openid-connect/token | \
           jq -r .access_token
       )
       ```
    2. Create a realm:
       ```shell
       curl -f -s -S \
         -H "Authorization: Bearer $ADMIN_TOKEN" \
         -H 'Content-Type: application/json' \
         -d '{"realm": "myrealm5","enabled": true}' \
         http://localhost:8080/admin/realms
       ```
    3. Create a client:
        1. Create client:
            ```shell
            curl -f -s -S \
              -H "Authorization: Bearer $ADMIN_TOKEN" \
              -H 'Content-Type: application/json' \
              -d '{"clientId": "myclient5", "enabled": true, "redirectUris": ["*"], "standardFlowEnabled": true, "serviceAccountsEnabled": true}' \
              http://localhost:8080/admin/realms/myrealm5/clients
            ```
        2. Get client's `id`:
            ```shell
            export CLIENT_ID=$(
              curl -f -s -S \
                -H "Authorization: Bearer $ADMIN_TOKEN" \
                http://localhost:8080/admin/realms/myrealm5/clients?clientId=myclient5 | \
              jq -r .[0].id
            )
            ```
        3. Get client's `secret`:
            ```shell
            export CLIENT_SECRET=$(
              curl -f -s -S \
                -H "Authorization: Bearer $ADMIN_TOKEN" \
                http://localhost:8080/admin/realms/myrealm5/clients/$CLIENT_ID/client-secret | \
              jq -r .value
            )
            echo "client_secret=$CLIENT_SECRET" > /tmp/oauth-client.properties
            ```
    4. Create a user:
       ```shell
       curl -f -s -S \
         -H "Authorization: Bearer $ADMIN_TOKEN" \
         -H 'Content-Type: application/json' \
         -d '{"username": "myuser5", "enabled": true, "firstName": "John", "lastName": "Smith", "credentials": [{"type": "password", "value": "myuser5pass", "temporary": false}]}' \
         http://localhost:8080/admin/realms/myrealm5/users
       ```
3. Run Resource Server
    1. Run class `spring.boot2.oauth2.resourceserver.ResourceServerApp`
    2. (optional) Test
        1. Get client's access token:
           ```shell
           export CLIENT_ACCESS_TOKEN=$(
             curl -f -s -S \
               -H 'Content-Type: application/x-www-form-urlencoded' \
               -d "grant_type=client_credentials&client_id=myclient5&client_secret=$CLIENT_SECRET" \
               http://localhost:8080/realms/myrealm5/protocol/openid-connect/token | \
             jq -r .access_token
           )
           ```
        2. Get resource: `curl -H "Authorization: Bearer $CLIENT_ACCESS_TOKEN" http://localhost:8082/api/greet`
4. Run OAuth Client
    1. Run main class: `spring.boot2.oauth2.client.authorization_code.OAuthClientApp`
    2. Test "Authentication Code" flow
        1. User credentials `myuser5`/`myuser5pass`
        2. Get local resource (private tab): http://localhost:8081/authorizationCode/localResource
        3. Get resource from the Resource Server (private tab): http://localhost:8081/authorizationCode/resourceServer
    3. Test "Client Credentials" flow
        1. Get resource from the Resource Server
           1. Using`@RegisteredOAuth2AuthorizedClient`: `curl http://localhost:8081/clientCredentials/resourceServer/autowire`
           2. Manually: `curl http://localhost:8081/clientCredentials/resourceServer/manual`
