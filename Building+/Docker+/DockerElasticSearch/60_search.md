# Search documents

## Match query
```
curl -XGET "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?pretty" -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'
```

## With document size
```
curl -XGET "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?pretty" -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  },
  "stored_fields" : ["_size"]
}'
```
