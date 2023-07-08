# az CLI

## Info
Help: `az -h`
Help for a command: `az storage -h`
Help for a sub-command: `az storage fs -h`
Version (human): `az --version`
Version (JSON): `az version`

## Login
Login (using web-browser): `az login`
Login (using device code): `az login --use-device-code`
Display current user: `az account show`

## Set Account in envs
Instead of specifying `--account-name` and `--account-key` in each request it's possible to use env vars:
```
export AZURE_STORAGE_ACCOUNT=...
export AZURE_STORAGE_KEY=...
```

## Storage
List storage accounts: `az storage account list`
List containers in an account: `az storage container list --account-name yaaltest`
List blobs in a container: `az storage blob list --account-name yaaltest --container-name container1`
Upload local file to storage: `az storage blob upload --account-name <storage-account-name> --account-key <account-key> --name <blob-name> --type block --file <local-file-path> --container-name <container-name>`
Is ABFSS file exist: `az storage fs file exists --account-name <storage-account-name> --account-key <account-key> --file-system program-cic-rm-dev-rm-dev3 --path return-job-config/stream-general-config.json`
