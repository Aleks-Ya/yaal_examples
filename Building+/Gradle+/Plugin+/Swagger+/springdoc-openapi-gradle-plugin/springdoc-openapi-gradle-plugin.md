# Generate "OpenAPI definition" with SpringDoc openapi-gradle-plugin

Docs: https://springdoc.org/v2/#gradle-plugin
GitHub: https://github.com/springdoc/springdoc-openapi-gradle-plugin

## Unit tests
`gradle clean test`

## Run Spring app
1. `gradle run`
2. Open http://localhost:8080/custom in browser
3. Open http://localhost:8080/v3/api-docs in browser

## Generate OpenAPI definition
1. `gradle clean generateOpenApiDocs`
2. OpenAPI JSON: `build/openapi.json`
