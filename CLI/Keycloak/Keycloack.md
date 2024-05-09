# Keycloack

## Docker
### Docs
Docker image: https://quay.io/repository/keycloak/keycloak
Docker Getting Started: https://www.keycloak.org/getting-started/getting-started-docker
Sources: https://github.com/keycloak/keycloak/tree/main/quarkus/container

### Run
1. Run: `docker run --name=keycloak --rm --network host -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
2. Open Admin Console: http://localhost:8080/admin (password `admin`/`admin`)

### Admin CLI connection
1. Run: `docker exec -it -e PATH=$PATH:/opt/keycloak/bin/ keycloak bash`
2. Authenticate: `kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin --client admin-cli`
3. Test: `kcadm.sh get realms`
