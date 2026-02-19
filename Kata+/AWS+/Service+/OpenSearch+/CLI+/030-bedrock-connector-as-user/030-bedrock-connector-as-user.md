# 030-bedrock-connector-as-user

## Task
Use Bedrock connector to generate embeddings (as an IAM User).

## Steps
1. Start an OpenSearch domain: 
	`DevOps+/AWS+/CDK+/PythonCdkProject/src/python_cdk_project/minimal_opensearch_domain_stack_33.py`
2. As the Root User
	1. Create the Root User terminal
		1. Open a new terminal
		2. Change current dir
		3. Set environment variables
			```shell
			set -x
			export AWS_PROFILE=acc3
			export USER=kata-user-bedrock-connector-as-user
			export PROFILE_USER=kata-prifile-bedrock-connector-as-user
			export REGION=us-east-1
			export POLICY_NAME_USER=kata-policy-bedrock-connector-as-user-user
			export POLICY_ARN_USER=arn:aws:iam::121846058060:policy/$POLICY_NAME_USER
			export POLICY_ARN_OPENSEARCH=arn:aws:iam::aws:policy/AmazonOpenSearchServiceReadOnlyAccess
			```
		4. Test: `aws sts get-caller-identity`
	2. Create an IAM user:
		1. Create User: `aws iam create-user --user-name $USER`
		2. Create Access Key: `export CREDS=$(aws iam create-access-key --user-name $USER)`
		3. Configure a User profile:
			```shell
			export KEY_ID=$(echo $CREDS | jq -r '.AccessKey.AccessKeyId')
			aws --profile $PROFILE_USER configure set aws_access_key_id $KEY_ID
			aws --profile $PROFILE_USER configure set aws_secret_access_key \
				$(echo $CREDS | jq -r '.AccessKey.SecretAccessKey')
			aws --profile $PROFILE_USER configure set region $REGION
			```
	3. Test: `aws --profile $PROFILE_USER sts get-caller-identity`
	4. Grant permissions to the IAM User:
		```shell
		# es:DescribeDomain
		aws iam create-policy --policy-name $POLICY_NAME_USER --policy-document file://user-policy.json
		aws iam attach-user-policy --user-name $USER --policy-arn $POLICY_ARN_OPENSEARCH
		# es:ESHttpGet, iam:CreateRole, iam:AttachRolePolicy, iam:PassRole, iam:DetachRolePolicy, iam:DeleteRole
		aws iam attach-user-policy --user-name $USER --policy-arn $POLICY_ARN_USER
		```
2. As the IAM User
	1. Create the IAM User terminal
		1. Open a new terminal
		2. Change current dir
		3. Set environment variables
			```shell
			set -x
			alias es='awscurl --service es'
			export USER=kata-user-bedrock-connector-as-user
			export PROFILE_USER=kata-prifile-bedrock-connector-as-user
			export DOMAIN=domain33
			export POLICY_BEDROCK=arn:aws:iam::aws:policy/AmazonBedrockLimitedAccess
			export ROLE_BEDROCK=kata-role-bedrock-connector-as-user
			export PIPELINE_INGEST=kata-pipeline-bedrock-connector-as-user-ingest
			export PIPELINE_SEARCH=kata-pipeline-bedrock-connector-as-user-search
			export INDEX=kata-index-bedrock-connector-as-user
			export AWS_PROFILE=$PROFILE_USER
			```
		4. Test: `aws sts get-caller-identity`
	2. Get cluster URL: 
		1. Get URL:
			```shell
			export ES=http://$(aws opensearch describe-domain --query 'DomainStatus.Endpoint' \
				--output text --domain-name $DOMAIN)
			```
		2. Test cluster status: `es $ES`
	3. Create a Bedrock Connector:
		1. Create a Role for Bedrock Connector:
			```shell
			aws iam create-role --role-name $ROLE_BEDROCK \
				--assume-role-policy-document file://bedrock-trust-policy.json
			aws iam attach-role-policy --role-name $ROLE_BEDROCK --policy-arn $POLICY_BEDROCK
			```
		2. Create Connector:
			1. (?) Enable Connector URL: `es -X PUT $ES/_cluster/settings -d @cluster-settings.json | jq`
			2. Create Connector:
				```shell
				export CONNECTOR_ID=$(es -X POST $ES/_plugins/_ml/connectors/_create \
					-d @create-connector.json | jq -r .connector_id)
				```
			3. Verify Connector: `es $ES/_plugins/_ml/connectors/$CONNECTOR_ID | jq`
	4. Register Model
		1. Register a Model Group:
			```shell
			export MODEL_GROUP_ID=$(es -X POST $ES/_plugins/_ml/model_groups/_register \
				-d @register-model-group.json | jq -r .model_group_id)
			```
		2. Register Model:
			```shell
			cat register-model-template.json | envsubst > register-model.json
			export REGISTER_MODEL_RESPONSE=$(es -X POST $ES/_plugins/_ml/models/_register -d @register-model.json)
			export REGISTER_MODEL_TASK_ID=$(echo $REGISTER_MODEL_RESPONSE | jq -r .task_id)
			export MODEL_ID=$(echo $REGISTER_MODEL_RESPONSE | jq -r .model_id)
			```
		3. Get Task status (need `COMPLETED`): `es $ES/_plugins/_ml/tasks/$REGISTER_MODEL_TASK_ID | jq`
		4. Deploy Model: `es -X POST $ES/_plugins/_ml/models/$MODEL_ID/_deploy | jq`
		5. Test Model 1: `es -X POST $ES/_plugins/_ml/_predict/TEXT_EMBEDDING/$MODEL_ID -d @try-model-1.json | jq -c`
		6. Test Model 2: `es -X POST $ES/_plugins/_ml/models/$MODEL_ID/_predict -d @try-model-2.json | jq -c`
	5. Create an Ingest Pipeline:
		1. Create Ingest Pipeline:
			```shell
			cat create-ingest-pipeline-template.json | envsubst > create-ingest-pipeline.json
			es -X PUT $ES/_ingest/pipeline/$PIPELINE_INGEST -d @create-ingest-pipeline.json
			```
		2. Simulate Ingest Pipeline:
			```shell
			es -X POST $ES/_ingest/pipeline/$PIPELINE_INGEST/_simulate?verbose \
				-d @simulate-ingest-pipeline.json | jq -c
			```
	6. Create an Index
		1. Delete Index: `es -X DELETE $ES/${INDEX}?ignore_unavailable=true`
		2. Create Index: `es -X PUT $ES/${INDEX} -d @create-index.json`
		3. Index bulk:
			```shell
			cat index-bulk-template.json | envsubst > index-bulk.json
			es -X POST $ES/_bulk -d @index-bulk.json | jq
			```
		4. List all docs: `es $ES/$INDEX/_search | jq -c`
	7. Vector search:
		```shell
		cat vector-search-template.json | envsubst > vector-search.json
		es $ES/$INDEX/_search -d @vector-search.json | jq
		```
	8. Create a search pipeline:
		`es -X PUT $ES/_search/pipeline/$PIPELINE_SEARCH -d @create-search-pipeline.json | jq`
	9. Hybrid search:
		```shell
		cat hybrid-search-template.json | envsubst > hybrid-search.json
		es $ES/$INDEX/_search?search_pipeline=$PIPELINE_SEARCH -d @hybrid-search.json | jq
		```

## Cleanup
1. As the IAM User
	1. Delete the index: `es -X DELETE $ES/${INDEX}?ignore_unavailable=true`
	2. Undeploy model: `es -X POST $ES/_plugins/_ml/models/$MODEL_ID/_undeploy | jq`
	3. Delete model: `es -X DELETE $ES/_plugins/_ml/models/$MODEL_ID | jq`
	4. Delete the model group: `es -X DELETE $ES/_plugins/_ml/model_groups/$MODEL_GROUP_ID | jq`
	5. Delete connector: `es -X DELETE $ES/_plugins/_ml/connectors/$CONNECTOR_ID | jq`
	6. Delete the Ingest Pipeline: `es -X DELETE $ES/_ingest/pipeline/$PIPELINE_INGEST`
	7. Delete the Search Pipeline: `es -X DELETE $ES/_search/pipeline/$PIPELINE_SEARCH`
	8. Delete the Role:
		```shell
		aws iam detach-role-policy --role-name $ROLE_BEDROCK --policy-arn $POLICY_BEDROCK
		aws iam delete-role --role-name $ROLE_BEDROCK
		```
	9. Close the terminal
2. As the Root User
	1. Delete User Policy:
		```shell
		aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN_OPENSEARCH
		aws iam detach-user-policy --user-name $USER --policy-arn $POLICY_ARN_USER
		aws iam delete-policy --policy-arn $POLICY_ARN_USER
		```
	2. Delete profile: `crudini --del ~/.aws/credentials $PROFILE_USER`
	3. Delete Access Key: `aws iam delete-access-key --user-name $USER --access-key-id $KEY_ID`
	4. Delete User `aws iam delete-user --user-name $USER`
	5. Close the terminal

3. Shutdown the OpenSearch domain

## History
- 2026-02-18 success
- 2026-02-19 success
