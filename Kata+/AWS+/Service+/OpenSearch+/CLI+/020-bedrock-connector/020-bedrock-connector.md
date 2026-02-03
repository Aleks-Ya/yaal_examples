# 020-bedrock-connector

## Task
Use Bedrock connector to generate embeddings.

## Steps
1. Open a new terminal
2. Change current dir
3. Set environment variables
	```shell
	set -x
	alias es='awscurl --service es'
	export AWS_PROFILE=acc3
	export DOMAIN=domain1
	export ROLE=kata-role-bedrock-connector
	export INDEX=kata-index-bedrock-connector
	export PIPELINE=bedrock-auto-embed-pipeline
	```
4. Start an OpenSearch domain: 
	1. Create a domain: 
		`~/yaal_examples/DevOps+/AWS+/CDK+/PythonCdkProject/src/python_cdk_project/minimal_opensearch_domain_stack.py`
	2. Get cluster URL: 
		```shell
		export ES=http://$(aws opensearch describe-domain --query 'DomainStatus.Endpoint' \
			--output text --domain-name $DOMAIN)
		```
	3. Test cluster status: `es $ES`
5. Create a Role for Bedrock Connector:
	1. Create a Role:
		`aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
	2. Attach Policy:
		`aws iam attach-role-policy --role-name $ROLE --policy-arn arn:aws:iam::aws:policy/AmazonBedrockLimitedAccess`
6. Create a Bedrock Connector:
	1. (?) Enable Connector URL: `es -X PUT $ES/_cluster/settings -d @cluster-settings.json`
	2. Create Connector:
		```shell
		export CONNECTOR_ID=$(es -X POST $ES/_plugins/_ml/connectors/_create \
			-d @create-bedrock-connector.json | jq -r .connector_id)
		```
	3. Verify Connector: `es $ES/_plugins/_ml/connectors/$CONNECTOR_ID | jq`
7. Register Model
	1. Register Model:
		```shell
		cat register-model-template.json | envsubst > register-model.json
		export REGISTER_MODEL_RESPONSE=$(es -X POST $ES/_plugins/_ml/models/_register -d @register-model.json)
		export REGISTER_MODEL_TASK_ID=$(echo $REGISTER_MODEL_RESPONSE | jq -r .task_id)
		export MODEL_ID=$(echo $REGISTER_MODEL_RESPONSE | jq -r .model_id)
		```
	2. Get Task status (need `COMPLETED`): `es $ES/_plugins/_ml/tasks/$REGISTER_MODEL_TASK_ID`
8. Deploy Model: `es -X POST $ES/_plugins/_ml/models/$MODEL_ID/_deploy`
9. Test Model: `es -X POST $ES/_plugins/_ml/_predict/TEXT_EMBEDDING/$MODEL_ID -d @try-model.json | jq`
10. Create an Ingest Pipeline: 
	1. Create Ingest Pipeline:
		```shell
		cat create-ingest-pipeline-template.json | envsubst > create-ingest-pipeline.json
		es -X PUT $ES/_ingest/pipeline/$PIPELINE -d @create-ingest-pipeline.json
		```
	2. Simulate Ingest Pipeline:
		`es -X POST $ES/_ingest/pipeline/$PIPELINE/_simulate -d @simulate-ingest-pipeline.json | jq`
11. Create an Index
	1. Delete Index: `es -X DELETE $ES/${INDEX}?ignore_unavailable=true`
	2. Create Index: `es -X PUT $ES/${INDEX} -d @create-index.json`
	3. Index bulk:
		```shell
		cat index-bulk-template.json | envsubst > index-bulk.json
		es -X POST $ES/_bulk -d @index-bulk.json | jq
		```
	4. List all docs: `es $ES/$INDEX/_search | jq`
12. Vector search:
	```shell
	cat vector-search-template.json | envsubst > vector-search.json
	es $ES/$INDEX/_search -d @vector-search.json | jq
	```
13. Create a search pipeline: `es -X PUT $ES/_search/pipeline/nlp-search-pipeline -d @create-search-pipeline.json | jq`

## Cleanup
1. Shutdown the OpenSearch domain

## History
