# SwaggerCodeGen

## Docs
GitHub: https://github.com/swagger-api/swagger-codegen

## Install
`brew install swagger-codegen`

## Commands
### Info
Help: `swagger-codegen -h`
Help about a command: `swagger-codegen generate -h`
Configuration help for a language: `swagger-codegen config-help -l java`
Version: `swagger-codegen version`
List available programming languages: `swagger-codegen langs`

## Generate documentation (in current dir)
### Maven + Gradle + SBT projects
1. `swagger-codegen generate -i https://petstore.swagger.io/v2/swagger.json -l java`
2. Use `pom.xml`, `build.gradle` or `build.sbt`
### HTML
1. `swagger-codegen generate -i https://petstore.swagger.io/v2/swagger.json -l html`
2. Open `index.html`
### HTML2
1. `swagger-codegen generate -i https://petstore.swagger.io/v2/swagger.json -l html2`
2. Open `index.html`
### Dynamic HTML
1. `swagger-codegen generate -i https://petstore.swagger.io/v2/swagger.json -l dynamic-html`
2. `npm install`
3. `node .`
4. Open http://localhost:8002
