# aws CLI

## Install
Latest version: 
	1. Install `sudo apt install -y unzip`
	2. Follow [Guide](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
	3. Close WSL window
PIP (outdated version 1): `pip3 install awscli`

## Commands
[Command reference](https://docs.aws.amazon.com/cli/latest/reference/#available-services)
Default config file location: `~/.aws/config`

Version: `aws --version`
Choose output format (`text`, `table`, `json`, etc.): `aws s3api --output table list-buckets`

### Authentication
Check credential: `aws sts get-caller-identity`
Via environment variables:
```shell
export AWS_ACCESS_KEY_ID=abc
export AWS_SECRET_ACCESS_KEY=xyz
aws sts get-caller-identity
```

### help
Help: `aws help`
Help for a command: `aws ec2 help`
Help for a sub-command: `aws configure list-profiles help`

### OpenSearch
Help: `aws opensearch help`
List domains: `aws opensearch list-domain-names`
List packages: `aws opensearch describe-packages`

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

### API Gateway
List all APIs: `aws apigatewayv2 get-apis`
Show API details: `aws apigatewayv2 get-api --api-id 58150tndk9`
List API routes: `aws apigatewayv2 get-routes --api-id 58150tndk9`
Show API route details: `aws apigatewayv2 get-route --api-id 58150tndk9 --route-id zzbuukm`
Invoke an API route: `curl <ApiEndpoint>/<Route>` (e.g. `curl https://58150tndk9.execute-api.us-east-1.amazonaws.com/api-handler`)
CORS Preflight Request: `curl -i -X OPTIONS https://2qydkfnxua.execute-api.us-east-1.amazonaws.com -H "Origin: http://website-8210834172.s3-website-us-east-1.amazonaws.com" -H "Access-Control-Request-Headers: content-type" -H "Access-Control-Request-Method: POST" -H "Access-Control-Request-Headers: content-type"`

### SNS
Send SMS to phone: `aws sns publish --phone-number "+12345678901" --message "Your message here"`
List Sandbox destination phone numbers: `aws sns list-sms-sandbox-phone-numbers`
