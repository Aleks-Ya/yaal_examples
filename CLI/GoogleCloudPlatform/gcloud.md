# gcloud CLI (Google Cloud SDK)

## Install
`sudo snap install google-cloud-cli --classic`

## Commands
Show MAN page: `gcloud help`, `gcloud --help`
Initialize after installation: `gcloud init`

### Projects
List projects: `gcloud projects list`
Create a binding: `gcloud projects add-iam-policy-binding your-project-id --member="serviceAccount:your-service-account-name@your-project-id.iam.gserviceaccount.com" --role="roles/storage.admin"`

### Info
Display properties and other info: `gcloud info`
Show overall information: `gcloud info`

### Files
List available buckets: `gsutil ls`
List files in a bucket: `gsutil ls gs://yaal-standard-1/backup-zotero`

### Config
Get current project: `gcloud config get-value project`
Choose default project (use ID, but not Project Name): `gcloud config set project id-of-my-project`
List properties: `gcloud config list`

### Auth
Log in: `gcloud auth login`
Show current login: `gcloud auth list`
Log out of all accounts: `gcloud auth revoke --all`
Authenticate as a service account: `gcloud auth activate-service-account --key-file=/home/aleks/.gcloud/key.json`

## AIM
List service accounts: `gcloud iam service-accounts list`
Describe a service account: `gcloud iam service-accounts describe my-service-account@my-project-id.iam.gserviceaccount.com`
Create a service account: `gcloud iam service-accounts create my-account`
Revoke a service account key: `gcloud iam service-accounts keys delete my-key-id --iam-account my-service-account@my-project-id.iam.gserviceaccount.com`
List Service Account keys: `gcloud iam service-accounts keys list --iam-account my-service-account@my-project-id.iam.gserviceaccount.com`
Delete a Service Account key: `gcloud iam service-accounts keys delete my-key-id --iam-account my-service-account@my-project-id.iam.gserviceaccount.com`
Generate and download a JSON key file: `gcloud iam service-accounts keys create ~/key.json --iam-account my-service-account@my-project-id.iam.gserviceaccount.com`
