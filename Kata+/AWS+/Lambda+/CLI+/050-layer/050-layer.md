# 050-layer

## Task
Create a Lambda Function that uses code from a Layer.

## Steps
1. Change directory
2. Create a Layer
	1. Pack ZIP: `cd text-layer; zip -r ../text-layer.zip *; cd ..`
	2. Create a layer version: `aws lambda publish-layer-version --layer-name text-layer --compatible-runtimes python3.13 --zip-file fileb://text-layer.zip`
3. Create functions
	1. Create a Function
		1. Create an Execution Role
			1. Create a Role: `aws iam create-role --role-name FunctionLayeredRole --assume-role-policy-document file://trust-policy.json`
			2. Attach a Policy: `aws iam put-role-policy --role-name FunctionLayeredRole --policy-name FunctionLayeredPolicy --policy-document file://inline-policy.json`
		2. Create a Deployment Package: `cd function-layered; zip -r ../function-layered.zip *; cd ..`
		3. Create a Function:
			```shell
			aws lambda create-function \
	  			--function-name function-layered \
	  			--runtime python3.13 \
	  			--role arn:aws:iam::523633434047:role/FunctionLayeredRole \
	  			--handler handler.lambda_handler \
	  			--zip-file fileb://function-layered.zip \
	  			--layers arn:aws:lambda:us-east-1:523633434047:layer:text-layer:1
			```
		4. Test function: `aws lambda invoke --function-name function-layered /dev/stdout`

## Cleanup
1. Delete Function: `aws lambda delete-function --function-name function-layered`
2. Delete Layer: `aws lambda delete-layer-version --layer-name text-layer --version-number 1`
3. Delete Execution Role: 
	1. Delete policy: `aws iam delete-role-policy --role-name FunctionLayeredRole --policy-name FunctionLayeredPolicy`
	2. Delte role: `aws iam delete-role --role-name FunctionLayeredRole`

## History

Update:
- `aws lambda publish-layer-version --layer-name text-layer --compatible-runtimes python3.13 --zip-file fileb://text-layer.zip`
- `aws lambda update-function-configuration --function-name function-layered --layers arn:aws:lambda:us-east-1:523633434047:layer:text-layer:3`
- `aws lambda update-function-code --function-name function-layered --zip-file fileb://function-layered.zip --publish`
