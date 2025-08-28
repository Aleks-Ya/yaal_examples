# 010-produce-and-consume-data-stream

## Task

Create a Kinesis Data Stream. Produce a Record to it. Consume the Record.

## Setup

1. Create a Data Stream
    1. Data stream name: `ds1`
    2. Capacity mode: `On-demand`
2. Produce a Record using AWS CLI
    1. Encode the text in Base64: `echo "Hello, Kinesis!" | base64` -> `SGVsbG8sIEtpbmVzaXMhCg==`
    2. Produce a Record: `aws kinesis put-record --stream-name ds1 --partition-key key1 --data SGVsbG8sIEtpbmVzaXMhCg==`
    3. Check the Record in AWS Console
        1. Open Data Stream `ds1`
        2. Open `Data Viewer` tab
        3. Shard: `shardId-000000000003` (returned by `aws kinesis put-record`)
        4. Starting position: `At sequence number`
        5. Sequence number: `49647323058057281896141215493469653089078398483821494322`
           (returned by `aws kinesis put-record`)
        6. Click `Get Record`
3. Consume the Record using AWS CLI
    1. Create a shard iterator:
       ```
       aws kinesis get-shard-iterator --stream-name ds1 \
       --shard-id shardId-000000000003 \
       --shard-iterator-type AT_SEQUENCE_NUMBER \
       --starting-sequence-number 49647323058057281896141215493469653089078398483821494322
       ```
    2. Get the record: `aws kinesis get-records --shard-iterator AAAAAAAAAAFW5...`
    3. Decode the text: `echo SGVsbG8sIEtpbmVzaXMhCg== | base64 -d`

## Cleanup

1. Delete Data Steam `ds1`
