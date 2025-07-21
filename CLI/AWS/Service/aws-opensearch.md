# AWS OpenSearch API

Help: `aws opensearch help`

## Domain
List domains: `aws opensearch list-domain-names`
Describe a domain: `aws opensearch describe-domain --domain-name domain-1`

## Query
Send request to a cluster: `awscurl --service es https://search-domain-1-umrjsmqxw5xpwuqeuarcb2yno4.aos.us-east-1.on.aws`

## Package
List packages: `aws opensearch describe-packages`
List packages for a domain: `aws opensearch list-packages-for-domain --domain-name domain-1`
List ZIP-PLUGIN packages: `aws opensearch describe-packages --filters Name=PackageType,Value=ZIP-PLUGIN`
List TXT-DICTIONARY packages: `aws opensearch describe-packages --filters Name=PackageType,Value=TXT-DICTIONARY`
Delete a package: `aws opensearch delete-package --package-id G127015987`