# aws CLI

## Install
Latest version: 
	1. Install `sudo apt install -y unzip`
	2. Follow [Guide](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
	3. Close WSL window
PIP (outdated version 1): `pip3 install awscli`

## Commands
[Command reference](https://docs.aws.amazon.com/cli/latest/reference/#available-services)
Default config file location: ~/.aws/config

Version: `aws --version`
Check credential: `aws sts get-caller-identity`
Choose output format (`text`, `table`, `json`, etc.): `aws s3api --output table list-buckets`

### help
Help: `aws help`
Help for a command: `aws ec2 help`

### configure
Configure most used parameters: `aws configure`
Set default region: `aws configure set region eu-north-1`

### s3api
List S3 buckets: `aws s3api list-buckets`
List objects in a S3 bucket: `aws s3api list-objects --bucket qwasceitnsuryw7`
List objects by S3 URL: `aws s3 ls s3://yaal-backup/duplicity-backup-docs-vault/`
Show object info: `aws s3api head-object --bucket qwasceitnsuryw7 --key my1/bye.txt`
Download file from S3: `aws s3api get-object --bucket qwasceitnsuryw7 --key my1/bye.txt /tmp/bye.txt`
Delete S3 object: `aws s3api delete-object --bucket qwasceitnsuryw7 --key my1/`
Upload a file to S3:
```
aws s3api put-object --bucket yaal-test-bucket --key my_texts/bye.txt --body /tmp/bye.txt
#Link to the object: https://yaal-test-bucket.s3.eu-central-1.amazonaws.com/my_texts/bye.txt
```

### lambda
List Lambda Functions: `aws lambda list-functions`
List versions of a Function: `aws lambda list-versions-by-function --function-name my-function`
Upload ZIP to a Function:
```
aws lambda update-function-code --function-name my-function --zip-file fileb://my-function.zip --publish
```
Show Function configuration: `aws lambda get-function-configuration --function-name my-function`
Change Function Handler:
```
aws lambda update-function-configuration --function-name my-function --handler example.Handler::handleRequest
```
Change Function Timeout in sec (900 sec is max): `aws lambda update-function-configuration --function-name my-function --timeout 900`

### OpenSearch
Help: `aws opensearch help`
List domains: `aws opensearch list-domain-names`
