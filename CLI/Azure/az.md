# az CLI

## Install
Command: `curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash`
Docs: https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-linux

## Info
Help: `az -h`
Version (human): `az --version`
Version (JSON): `az version`

## Login
Login (using web-browser): `az login`
Display current user: `az account show`

## Storage
List storage accounts: `az storage account list`
List containers in an account: `az storage container list --account-name yaaltest`
List blobs in a container: `az storage blob list --account-name yaaltest --container-name container1`
