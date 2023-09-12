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
Show current configuration data: `aws configure list`
Configure most used parameters: `aws configure`
Show default region: `aws configure get region`
Set default region: `aws configure set region eu-north-1`
Set default output format: `aws configure set default.output json`
Show default output format: `aws configure get default.output`

### s3 (high-level)
List S3 buckets: `aws s3 ls`
Create a bucket: `aws s3 mb s3://iablokov471923643`
Upload a file to S3: `aws s3 cp my.txt s3://mybucket1/dir1/my.txt`
Delete a not-empty bucket: `aws s3 rb s3://iablokov471923643 --force`

### s3api (low-level)
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
List packages: `aws opensearch describe-packages`

### EC2
List all EC2 instances: `aws ec2 describe-instances`

### ec2-instance-connect
Help: `aws ec2-instance-connect help`
Connect to an EC2 instance by SSH without using keys: `aws ec2-instance-connect ssh --instance-id i-09a653b87321a3744`

### MSK (Kafka)
Help: `aws kafka help`
List clusters: `aws kafka list-clusters`

### Systems Manager - Parameter Store
List all parameters: `aws ssm describe-parameters`
Show single parameter: `aws ssm get-parameter --name param1 --with-decryption`
Show single parameter value: `aws ssm get-parameter --name param1 --with-decryption --query "Parameter.Value" --output text`
