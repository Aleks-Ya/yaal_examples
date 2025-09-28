# 010-maven-repo

## Task
Create a Maven repo in CodeArtifact. Push and pull a package from it.

## Setup
1. Create an IAM user
	1. Create User: `aws iam create-user --user-name kata-user-maven-repo`
	2. Create Access Key: `aws iam create-access-key --user-name kata-user-maven-repo`
	3. Add policy:
	```shell
	aws iam put-user-policy \
		--user-name kata-user-maven-repo \
		--policy-name CodeArtifactDomain \
		--policy-document file://policy.json
	```
	4. Configure CLI:
		1. `export AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE`
		2. `export AWS_SECRET_ACCESS_KEY=SECRET`
	5. Test access: `aws sts get-caller-identity`
2. Create a Repository
	1. Create a domain: `aws codeartifact create-domain --domain kata-domain-maven-repo`
	2. Create a repository: 
	```shell
	aws codeartifact create-repository \
		--domain kata-domain-maven-repo \
		--repository kata-repo-maven-repo \
		--upstreams upstreams=public:maven-central
	```
3. Use Repository
	1. Generate token: 
	```shell
	export CODEARTIFACT_AUTH_TOKEN=$(
		aws codeartifact get-authorization-token \
		--domain kata-domain-maven-repo \
		--query authorizationToken \
		--output text)
	```
	2. Read from repo: `mvn -s settings.xml -U clean package`
	3. Deploy to repo: `mvn -s settings.xml -U clean deploy`

## Cleanup
1. Delete IAM user
	1. Unset profile: `unset AWS_ACCESS_KEY_ID AWS_SECRET_ACCESS_KEY`
	2. Delete Access Key: `aws iam delete-access-key --user-name kata-user-maven-repo --access-key-id AKIAIOSFODNN7EXAMPLE`
	3. Delete Policy: `aws iam delete-user-policy --user-name kata-user-maven-repo --policy-name CodeArtifactDomain`
	4. Delete User `aws iam delete-user --user-name kata-user-maven-repo`
2. Delete Repository
	1. Delete repository: `aws codeartifact delete-repository --domain kata-domain-maven-repo --repository kata-repo-maven-repo`
	2. Delete Maven repo: `aws codeartifact delete-repository --domain kata-domain-maven-repo --repository maven-central-store`
	3. Delete domain: `aws codeartifact delete-domain --domain kata-domain-maven-repo`
