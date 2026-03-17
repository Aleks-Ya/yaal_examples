# MikeEngel JWT CLI

GitHub: https://github.com/mike-engel/jwt-cli

Install: `brew install mike-engel/jwt-cli/jwt-cli`

## Commands
Help: `jwt help`

Show a JWT token (string):
```shell
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30" | jwt -show -
```
Show a JWT token (file): `jwt -show token.jwt`
