# AWS Kinesis

List Data Streams: `aws kinesis list-streams`
List shards in a Data Stream: `aws kinesis list-shards --stream-name stream1`
Produce a Record to a Data Stream: `aws kinesis put-record --stream-name stream1 --partition-key key1 --data $(echo abc | base64)`
Creat a Data Stream: `aws kinesis create-stream --stream-name stream1 --shard-count 1`
Delete a Data Stream: `aws kinesis delete-stream --stream-name stream1`

Consume Record by its Sequence Number:
1. Create a shard iterator: 
```shell
aws kinesis get-shard-iterator --stream-name stream1 \
	--shard-id shardId-000000000003 \
	--shard-iterator-type AT_SEQUENCE_NUMBER \
	--starting-sequence-number 49647323058057281896141215493469653089078398483821494322
```
2. Get the record: `aws kinesis get-records --shard-iterator AAAAAAAAAAFW5...`
3. Decode the text: `echo SGVsbG8sIEtpbmVzaXMhCg== | base64 -d`