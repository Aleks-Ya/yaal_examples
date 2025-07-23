# AWS OpenSearch API

Help: `aws opensearch help`

## Domain
List domains: `aws opensearch list-domain-names`
Describe a domain: `aws opensearch describe-domain --domain-name domain-1`
Export domain endpoint to env var: 
```shell
export ES=http://$(aws opensearch describe-domain --query 'DomainStatus.Endpoint' --output text --domain-name domain-1)
```

## Query
Send request to a cluster: `awscurl --service es https://search-domain-1-umrjsmqxw5xpwuqeuarcb2yno4.aos.us-east-1.on.aws`

## Package
List packages: `aws opensearch describe-packages`
List ZIP-PLUGIN packages: `aws opensearch describe-packages --filters Name=PackageType,Value=ZIP-PLUGIN`
List TXT-DICTIONARY packages: `aws opensearch describe-packages --filters Name=PackageType,Value=TXT-DICTIONARY`
List packages by name: `aws opensearch describe-packages --filters Name=PackageName,Value=package1`
List packages associated with given domain: `aws opensearch list-packages-for-domain --domain-name domain-1`
List domains associated with given package: `aws opensearch list-domains-for-package --package-id F178690135`
Extract package ID by package name:
```shell
export PACKAGE_ID=$(aws opensearch describe-packages --filters Name=PackageName,Value=package1 --query 'PackageDetailsList[0].PackageID' --output text)
```
Delete a package: `aws opensearch delete-package --package-id G127015987`
Create a dictionary package:
```shell
aws opensearch create-package --package-name package1 --package-type TXT-DICTIONARY \
	--package-source S3BucketName=bucket1,S3Key=data/package.txt
```
Update a package: `aws opensearch update-package --package-id G127015987 --package-source S3BucketName=bucket1,S3Key=data/package.txt`
Show package versions: `aws opensearch get-package-version-history --package-id F178690135`
Associate package with domain: `aws opensearch associate-package --package-id G127015987 --domain-name domain-1`
Dissociate package from domain: `aws opensearch dissociate-package --package-id G127015987 --domain-name domain-1`
