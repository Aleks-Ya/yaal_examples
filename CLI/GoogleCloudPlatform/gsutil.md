# gsutil CLI

## Install
`snap install google-cloud-cli --classic`

## Commands
### Help
Help: `gsutil help`
Help about a command: `gsutil help ls`
Help about command options: `gsutil help options`
### LS
List all buckets: `gsutil ls`
List objects in a bucket: `gsutil ls gs://yaal-archive-1/`
List objects in a bucket (with statistics): `gsutil ls -L gs://yaal-archive-1/`
### CP
Upload object to a bucket: `gsutil cp /tmp/test.txt gs://yaal-archive-1/`
Download object from a bucket to local folder: `gsutil cp gs://yaal-archive-1/test_data_1.txt ~/tmp/`
### Other
Check object status: `gsutil stat gs://yaal-archive-1/test_data_1.txt`
Calculate hash of a local file: `gsutil hash file.txt``
