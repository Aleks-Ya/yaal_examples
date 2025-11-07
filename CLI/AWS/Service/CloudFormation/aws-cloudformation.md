# cloudformation CLI

List stacks: `aws cloudformation list-stacks`
Show stack details: `aws cloudformation describe-stacks --stack-name stack1`
Create a stack: `aws cloudformation create-stack --stack-name stack1 --template-body template.yaml`
Wait for stack creation: `aws cloudformation wait stack-create-complete --stack-name stack1`
Delete a stack: `aws cloudformation delete-stack --stack-name stack1`
Wait for stack deletion: `aws cloudformation wait stack-delete-complete --stack-name stack1`
Deploy a stack:
```shell
aws cloudformation deploy \
	--stack-name stack1 \
	--template-file template.yaml \
	--s3-bucket bucket1
```
