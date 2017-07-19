
# Commands for Elastic

## 1. Setup environment
```
export ES_LOGIN=elastic
export ES_PASS=changeme
export ES_HOST=localhost
export ES_PORT=9200
export ES_URL=http://${ES_LOGIN}:${ES_PASS}@${ES_HOST}:${ES_PORT}

export INDEX_NAME=people
export TYPE_NAME=ages
```

## 2. Index documents
```
curl -XPOST "$ES_URL/${INDEX_NAME}/${TYPE_NAME}?pretty" -d '{
  "name":"John",
  "age":"30"
}'
curl -XPOST "$ES_URL/${INDEX_NAME}/${TYPE_NAME}?pretty" -d '{
  "name":"Mary",
  "age":"25"
}'
curl -XPOST "$ES_URL/${INDEX_NAME}/${TYPE_NAME}?pretty" -d '{
  "name":"Simon",
  "age":"40"
}'
```

## 3. Search documents
```
curl -XGET "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?pretty" -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'
```
