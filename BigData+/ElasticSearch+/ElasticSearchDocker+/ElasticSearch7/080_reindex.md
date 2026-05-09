
#  Reindex
## Copy documents from an index to another

```shell
curl -XPOST $ES_URL/_reindex?pretty -d '{
  "source": {
    "index": "people"
  },
  "dest": {
    "index": "persons"
  }
}'
```
