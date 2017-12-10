# Search documents by Request Body Search

## Single index and type search
### Match query
```
# A match query
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'

# A match query with document size
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  },
  "stored_fields" : ["_size"]
}'
```

### Term query
```
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
      "term": {
          "email": "john@mail.ru"
      }
  }
}'
```

## Single index and Multi-type search
