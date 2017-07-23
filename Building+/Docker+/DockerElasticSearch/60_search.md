# Search documents
```
curl -XGET "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?pretty" -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'

curl -XGET "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?pretty" -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'
```
