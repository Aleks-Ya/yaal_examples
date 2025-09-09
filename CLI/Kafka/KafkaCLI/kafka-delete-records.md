# kafka-delete-records.sh CLI

Help: `kafka-delete-records.sh --help`
Version: `kafka-delete-records.sh --version`

## Clean a topic
Content of `offset.json`:
```json
{
    "partitions":
    [
        {
            "topic": "foo",
            "partition": 0,
            "offset": -1
        }
    ],
    "version": 1
}
```

Command:
```shell
kafka-delete-records.sh \
	--bootstrap-server localhost:9092 \
	--offset-json-file offset.json
```