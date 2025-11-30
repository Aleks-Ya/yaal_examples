# 010-produce-consume-data-stream

## Task
Create a Kinesis Data Stream. Produce a Record to it. Consume the Record.

## Steps
1. Create a Data Stream
    1. Data stream name: `kata-stream-produce-consume-data-stream`
    2. Capacity mode: `On-demand`
2. Produce a Record using AWS CLI
    1. Encode the text in Base64: `echo "Hello, Kinesis!" | base64` -> `SGVsbG8sIEtpbmVzaXMhCg==`
    2. Produce a Record: 
        ```shell
        aws kinesis put-record \
            --stream-name kata-stream-produce-consume-data-stream \
            --partition-key key1 \
            --data SGVsbG8sIEtpbmVzaXMhCg==
        ```
    3. Check the Record in AWS Console
        1. Open Data Stream `kata-stream-produce-consume-data-stream`
        2. Open `Data Viewer` tab
        3. Shard: `shardId-000000000003` (returned by `aws kinesis put-record`)
        4. Starting position: `At sequence number`
        5. Sequence number: `49647323058057281896141215493469653089078398483821494322`
           (returned by `aws kinesis put-record`)
        6. Click `Get Record`

## Cleanup
1. Delete Data Steam `kata-stream-produce-consume-data-stream`

## History
