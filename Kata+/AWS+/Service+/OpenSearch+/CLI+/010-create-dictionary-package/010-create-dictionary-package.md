# 010-create-dictionary-package

## Task
Create a Text Dictionary package in OpenSearch domain. Upload and use an dictionary package.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	export AWS_PROFILE=acc3
	export DOMAIN=domain1
	export BUCKET=kata-bucket-create-dictionary-package
	export PACKAGE_NAME=kata-package-create-dictionary-package
	```
4. Start an OpenSearch domain: 
	1. Create a domain: 
		`DevOps+/AWS+/CDK+/PythonCdkProject/src/python_cdk_project/minimal_opensearch_domain_stack_33.py`
	2. Get cluster URL: 
		```shell
		export ES=http://$(aws opensearch describe-domain --query 'DomainStatus.Endpoint' \
			--output text --domain-name $DOMAIN)
		```
	3. Test cluster status: `awscurl --service es $ES`
5. Create a package
	1. Create S3 bucket: `aws s3 mb s3://$BUCKET`
	2. Upload bundle to S3: `aws s3 cp dictionary-package.txt s3://$BUCKET/opensearch/`
	3. Create package:
		```shell
		aws opensearch create-package --package-name $PACKAGE_NAME --package-type TXT-DICTIONARY \
			--package-source S3BucketName=$BUCKET,S3Key=opensearch/dictionary-package.txt
		```
	4. Get Package ID:
		```shell
		export PACKAGE_ID=$(aws opensearch describe-packages --filters Name=PackageName,Value=$PACKAGE_NAME \
			--query 'PackageDetailsList[0].PackageID' --output text)
		```
	5. Assiciate package with domain: `aws opensearch associate-package --package-id $PACKAGE_ID --domain-name $DOMAIN`
6. Test package:
	1. Substitute package ID: `cat create-index-template.json | envsubst > create-index.json`
	2. Create an index: `awscurl --service es -XPUT $ES/index1 -d @create-index.json`
	3. List analyzers: `awscurl --service es $ES/index1/_settings | jq .index1.settings.index.analysis`
	4. Text analyzer: 
		```shell
		awscurl --service es -XPOST $ES/index1/_analyze \
			-d '{"analyzer":"my_analyzer","text":"I ate croissant."}' \
		| jq
		```
	(response should contains all 3 synonyms from `dictionary-package.txt`: `croissant`, `danish`, `pastry`)

## Cleanup
1. Delete S3 bucket: `aws s3 rb s3://$BUCKET --force`
2. Delete the package: 
	1. Delete index using the package: `awscurl --service es -XDELETE $ES/index1`
	2. Dissociate the package from domain: 
		`aws opensearch dissociate-package --package-id $PACKAGE_ID --domain-name $DOMAIN`
	3. See package status: `aws opensearch describe-packages --filters Name=PackageID,Value=$PACKAGE`
	4. Delete package: `aws opensearch delete-package --package-id $PACKAGE_ID`
3. Shutdown the OpenSearch domain

## History
- 2026-02-04 success
