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
# Single condition
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
      "term": {
          "email": "john@mail.ru"
      }
  }
}'

## Two condition (OR)
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
    "bool": {
      "should": [
        {
          "term": {
              "email": "john@mail.ru"
          }
        },
        {
          "term": {
            "age": 25
          }
        }
      ]
    }
  }
}'

## Two condition (AND)
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
    "bool": {
      "must": [
        {
          "term": {
              "email": "john@mail.ru"
          }
        },
        {
          "term": {
            "age": 30
          }
        }
      ]
    }
  }
}'
```

## Join search (single index, two types)
```
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty -d '{
  "query": {
      "term": {
          "email": "john@mail.ru"
      }
  }
}'

curl -XGET $ES_URL/$INDEX_NAME/$COMPANIES_TYPE_NAME/1?pretty
```
