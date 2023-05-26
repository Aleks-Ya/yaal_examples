# az CLI

## Info
Help: `az -h`
Version (human): `az --version`
Version (JSON): `az version`

## Login
Login (using web-browser): `az login`
Login (using device code): `az login --use-device-code`
Display current user: `az account show`

## Storage
List storage accounts: `az storage account list`
List containers in an account: `az storage container list --account-name yaaltest`
List blobs in a container: `az storage blob list --account-name yaaltest --container-name container1`
