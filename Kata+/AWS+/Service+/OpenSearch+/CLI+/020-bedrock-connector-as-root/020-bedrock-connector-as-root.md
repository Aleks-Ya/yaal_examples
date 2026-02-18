# 020-bedrock-connector-as-root

## Task
Use Bedrock connector to generate embeddings (as the Root User).

## Steps
1. Start an OpenSearch domain: 
	`DevOps+/AWS+/CDK+/PythonCdkProject/src/python_cdk_project/minimal_opensearch_domain_stack_33.py`
2. Open a new terminal
3. Change current dir
4. Set environment variables
	```shell
	set -x
	alias es='awscurl --service es'
	export AWS_PROFILE=acc3
	export DOMAIN=domain33
	export POLICY=arn:aws:iam::aws:policy/AmazonBedrockLimitedAccess
	export ROLE=kata-role-bedrock-connector-as-root
	export PIPELINE_INGEST=kata-pipeline-bedrock-connector-as-root-ingest
	export PIPELINE_SEARCH=kata-pipeline-bedrock-connector-as-root-search
	export INDEX=kata-index-bedrock-connector-as-root
	```
5. Get cluster URL: 
	1. Get URL:
		```shell
		export ES=http://$(aws opensearch describe-domain --query 'DomainStatus.Endpoint' \
			--output text --domain-name $DOMAIN)
		```
	3. Test cluster status: `es $ES`
6. Create a Bedrock Connector:
	1. Create a Role for Bedrock Connector:
		1. Create a Role:
			`aws iam create-role --role-name $ROLE --assume-role-policy-document file://trust-policy.json`
		2. Attach Policy: `aws iam attach-role-policy --role-name $ROLE --policy-arn $POLICY`	
	2. Create Connector:
		1. (?) Enable Connector URL: `es -X PUT $ES/_cluster/settings -d @cluster-settings.json | jq`
		2. Create Connector:
			```shell
			export CONNECTOR_ID=$(es -X POST $ES/_plugins/_ml/connectors/_create \
				-d @create-connector.json | jq -r .connector_id)
			```
		3. Verify Connector: `es $ES/_plugins/_ml/connectors/$CONNECTOR_ID | jq`
7. Register Model
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
8. Create an Ingest Pipeline:
	1. Create Ingest Pipeline:
		```shell
		cat create-ingest-pipeline-template.json | envsubst > create-ingest-pipeline.json
		es -X PUT $ES/_ingest/pipeline/$PIPELINE_INGEST -d @create-ingest-pipeline.json
		```
	2. Simulate Ingest Pipeline:
		`es -X POST $ES/_ingest/pipeline/$PIPELINE_INGEST/_simulate?verbose -d @simulate-ingest-pipeline.json | jq -c`
9. Create an Index
	1. Delete Index: `es -X DELETE $ES/${INDEX}?ignore_unavailable=true`
	2. Create Index: `es -X PUT $ES/${INDEX} -d @create-index.json`
	3. Index bulk:
		```shell
		cat index-bulk-template.json | envsubst > index-bulk.json
		es -X POST $ES/_bulk -d @index-bulk.json | jq
		```
	4. List all docs: `es $ES/$INDEX/_search | jq -c`
10. Vector search:
	```shell
	cat vector-search-template.json | envsubst > vector-search.json
	es $ES/$INDEX/_search -d @vector-search.json | jq
	```
11. Create a search pipeline: `es -X PUT $ES/_search/pipeline/$PIPELINE_SEARCH -d @create-search-pipeline.json | jq`
12. Hybrid search:
	```shell
	cat hybrid-search-template.json | envsubst > hybrid-search.json
	es $ES/$INDEX/_search?search_pipeline=$PIPELINE_SEARCH -d @hybrid-search.json | jq
	```

## Cleanup
1. Delete the index: `es -X DELETE $ES/${INDEX}?ignore_unavailable=true`
2. Undeploy model: `es -X POST $ES/_plugins/_ml/models/$MODEL_ID/_undeploy | jq`
3. Delete model: `es -X DELETE $ES/_plugins/_ml/models/$MODEL_ID | jq`
4. Delete the model group: `es -X DELETE $ES/_plugins/_ml/model_groups/$MODEL_GROUP_ID | jq`
5. Delete connector: `es -X DELETE $ES/_plugins/_ml/connectors/$CONNECTOR_ID | jq`
6. Delete the Ingest Pipeline: `es -X DELETE $ES/_ingest/pipeline/$PIPELINE_INGEST`
7. Delete the Search Pipeline: `es -X DELETE $ES/_search/pipeline/$PIPELINE_SEARCH`
8. Delete the Role:
	```shell
	aws iam detach-role-policy --role-name $ROLE --policy-arn $POLICY
	aws iam delete-role --role-name $ROLE
	```
9. Close the terminal
10. Shutdown the OpenSearch domain

## History
- 2026-02-18 success
