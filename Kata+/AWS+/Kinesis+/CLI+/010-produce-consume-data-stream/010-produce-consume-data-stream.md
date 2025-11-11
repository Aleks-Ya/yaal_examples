# 010-produce-consume-data-stream

## Task
Create a Kinesis Data Stream. Produce a Record to it. Consume the Record.

## Steps
1. Set env vars
	```shell
	set -x
	export STREAM=kata-stream-produce-consume-data-stream
    export SHARD=shardId-000000000000
	```
2. Create a Data Stream: `aws kinesis create-stream --stream-name $STREAM --shard-count 1`
3. Produce a Data Record:
    1. Encode the text in Base64: `export MESSAGE=$(echo hello1 | base64)`
    2. Produce a Record: `aws kinesis put-record --stream-name $STREAM --partition-key key1 --data $MESSAGE`
4. Consume the Data Record:
    1. Create a shard iterator:
        ```shell
        export ITERATOR=$(aws kinesis get-shard-iterator \
            --stream-name $STREAM \
            --shard-id $SHARD \
            --shard-iterator-type TRIM_HORIZON \
            | jq -r .ShardIterator)
        ```
    2. Get the record: `aws kinesis get-records --shard-iterator $ITERATOR`
    3. Decode the text: `echo aGVsbG8xCg== | base64 -d`

## Cleanup
1. Delete Data Steam: `aws kinesis delete-stream --stream-name $STREAM`
2. Delete env variables: `set +x; unset STREAM SHARD MESSAGE ITERATOR`

## History
- 2025-11-12 success
