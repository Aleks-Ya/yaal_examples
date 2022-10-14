# Sonar Web API (REST API)

Documentation: http://localhost:9000/web_api

## Prepare
Enrivonment vars: `export SONAR_USER=admin; export SONAR_PASS=admin; export SONAR_API=http://localhost:9000/api`

## User Tokens
List user tokens for current user: `curl -i -u $SONAR_USER:$SONAR_PASS $SONAR_API/user_tokens/search`
Generate a user token for current user: 
- With body: `curl -i -X POST -d name=token1 -u $SONAR_USER:$SONAR_PASS $SONAR_API/user_tokens/generate`
- With parameter: `curl -i -X POST -u $SONAR_USER:$SONAR_PASS $SONAR_API/user_tokens/generate?name=token2`
- Generate token and export to env var: 
	`export SONAR_TOKEN=$(curl -s -X POST -d name=token$RANDOM -u $SONAR_USER:$SONAR_PASS $SONAR_API/user_tokens/generate | jq -r .token)`
Revoke a token for current user:
- With body: `curl -i -X POST -d name=token1 -u $SONAR_USER:$SONAR_PASS $SONAR_API/user_tokens/revoke`
- With parameter: `curl -i -X POST -u $SONAR_USER:$SONAR_PASS $SONAR_API/user_tokens/revoke?name=token2`
