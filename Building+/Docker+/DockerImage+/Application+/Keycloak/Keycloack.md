# Keycloack

## Docs
Docker image: https://quay.io/repository/keycloak/keycloak
Docker Getting Started: https://www.keycloak.org/getting-started/getting-started-docker

## Run
1. Run: `docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev`
2. Open Admin Console: http://localhost:8080/admin (password `admin`/`admin`)
