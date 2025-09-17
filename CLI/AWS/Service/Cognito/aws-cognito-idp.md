# Cognito User Pool (Identity Provider) CLI

List User Pools: `aws cognito-idp list-user-pools --max-results 5`
Verify an Access Token: `aws cognito-idp get-user --access-token <access-token>`

Sign in with username and password (ALLOW_USER_PASSWORD_AUTH):
1. Sign Username and ClientID with HMAC:
```shell
echo -n "<UserName><AppClientID>" | 
openssl dgst -sha256 -hmac "app-client-secret" -binary | 
base64
``` 
2. Get an Access Token:
```shell
aws cognito-idp initiate-auth \
  --auth-flow USER_PASSWORD_AUTH \
  --client-id {AppClientID} \
  --auth-parameters USERNAME=John1,PASSWORD=johnPass_1,SECRET_HASH={hmac-signature}
```
3. Review the ID Token: `echo "TheIdToken" | jwt -show -`
4. Review the Access Token: `echo "TheAccessToken" | jwt -show -`
5. Test the Access Token: `aws cognito-idp get-user --access-token <access-token>`
