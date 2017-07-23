
#  Reindex
## Copy documents from an index to another

```
curl -XPOST "$ES_URL/_reindex?pretty" -d '{
  "source": {
    "index": "people"
  },
  "dest": {
    "index": "persons"
  }
}'
```
