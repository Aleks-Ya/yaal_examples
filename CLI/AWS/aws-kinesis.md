# AWS Kinesis

List Data Streams: `aws kinesis list-streams`
List shards in a Data Stream: `aws kinesis list-shards --stream-name ds1`
Produce a Record to a Data Stream: `aws kinesis put-record --stream-name ds1 --partition-key key1 --data $(echo abc | base64)`

Consume Record by its Sequence Number:
1. Create a shard iterator: 
```
aws kinesis get-shard-iterator --stream-name ds1 \
--shard-id shardId-000000000003 \
--shard-iterator-type AT_SEQUENCE_NUMBER \
--starting-sequence-number 49647323058057281896141215493469653089078398483821494322
```
2. Get the record: `aws kinesis get-records --shard-iterator AAAAAAAAAAFW5...`
3. Decode the text: `echo SGVsbG8sIEtpbmVzaXMhCg== | base64 -d`