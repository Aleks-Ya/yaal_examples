# 010-create-dictionary-package

## Task
Create a Text Dictionary package in OpenSearch domain. Upload and use an dictionary package.

## Steps
1. Start an OpenSearch domain: 
	1. Create the domain: 
	see `~/yaal_examples/DevOps+/AWS+/CDK+/PythonCdkProject/src/python_cdk_project/minimal_opensearch_domain_stack.py`
	2. Set env var with cluster url: 
	`export ES=http://$(aws opensearch describe-domain --query 'DomainStatus.Endpoint' --output text --domain-name kata-domain-create-dictionary-package)`
	3. Test cluster status: `awscurl --service es $ES`
2. Create S3 bucket: `aws s3 mb s3://kata-bucket-create-dictionary-package`
3. Upload bundle to S3: `aws s3 cp dictionary-package.txt s3://kata-bucket-create-dictionary-package/opensearch/`
4. Create package and extract ID:
	```shell
	aws opensearch create-package --package-name kata-package-create-dictionary-package --package-type TXT-DICTIONARY \
		--package-source S3BucketName=kata-bucket-create-dictionary-package,S3Key=opensearch/dictionary-package.txt
	export PACKAGE_ID=$(aws opensearch describe-packages --filters Name=PackageName,Value=kata-package-create-dictionary-package --query 'PackageDetailsList[0].PackageID' --output text)
	```
5. Assiciate package with domain: `aws opensearch associate-package --package-id $PACKAGE_ID --domain-name kata-domain-create-dictionary-package`
6. Test package:
	1. Substitute package ID: `envsubst < create-index-template.json > create-index.json`
	2. Create an index: `awscurl --service es -XPUT $ES/index1 -d @create-index.json`
	3. List analyzers: `awscurl --service es $ES/index1/_settings | jq .index1.settings.index.analysis`
	4. Text analyzer: 
	`awscurl --service es -XPOST $ES/index1/_analyze -d '{"analyzer":"my_analyzer","text":"I ate croissant."}' | jq`
	(response should contains all 3 synonyms from `dictionary-package.txt`: `croissant`, `danish`, `pastry`)

## Cleanup
1. Delete S3 bucket: `aws s3 rb s3://kata-bucket-create-dictionary-package --force`
2. Delete the package: 
	1. Delete index using the package: `awscurl --service es -XDELETE $ES/index1`
	2. Dissociate the package from domain: `aws opensearch dissociate-package --package-id $PACKAGE_ID --domain-name kata-domain-create-dictionary-package`
	2. Delete package: `aws opensearch delete-package --package-id $PACKAGE_ID`
3. Shutdown the OpenSearch domain

## History
