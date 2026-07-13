# AWS s3control CLI

## Access Point
List Access Points in an Account: `aws s3control list-access-points --account-id 523633434047`
List Access Points on a Bucket: `aws s3control list-access-points --account-id 523633434047 --bucket bucket1`
Get the Alias of an Access Point: 
	`aws s3control get-access-point --account-id 523633434047 --name point1 --query Alias --output text`
Create an Access Point: 
```shell
aws s3control create-access-point \
	--account-id 123456789012 \
	--name my-access-point \
	--bucket my-bucket \
```
Delete an Access Point: `aws s3control delete-access-point --account-id 523633434047 --name point1`
