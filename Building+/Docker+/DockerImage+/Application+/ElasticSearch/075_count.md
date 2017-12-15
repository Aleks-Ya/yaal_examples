# Count API

# Match query
```
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_count?pretty -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'
```

# Term query
```
# Single condition
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_count?pretty -d '{
  "query": {
      "term": {
          "email": "john@mail.ru"
      }
  }
}'

## Two condition (OR)
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_count?pretty -d '{
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
curl -XGET $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/_count?pretty -d '{
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
