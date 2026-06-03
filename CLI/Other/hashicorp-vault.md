# HashiCorp Vault

## Install
Docs: https://developer.hashicorp.com/vault/install
```shell
brew tap hashicorp/tap
brew install hashicorp/tap/vault
```

## UI
Docs: https://developer.hashicorp.com/vault/tutorials/get-started/learn-ui
Run server locally: 
1. Run: `vault server -dev -dev-root-token-id root -dev-tls`
2. Open: https://127.0.0.1:8200
3. Login:
	1. Method: `token`
	2. Token: `root`
