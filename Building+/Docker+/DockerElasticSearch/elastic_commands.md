
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

## 3. Settings

```
# Show settings for all indexes
curl -XGET "$ES_URL/_all/_settings?pretty"

# Show settings for concrete index
curl -XGET "$ES_URL/${INDEX_NAME}/_settings?pretty"
```

## 3. Enable size

```
curl -XPUT "$ES_URL/${INDEX_NAME}/_mapping/${TYPE_NAME}" -d '{
      "properties": {
        "_size": {
          "enabled": true,
          "store" : true
        }
      }
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

curl -XGET "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?pretty" -d '{
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'
```

## 3. Scroll request

```
# Create a search with scroll
curl -XPOST "$ES_URL/${INDEX_NAME}/${TYPE_NAME}/_search?scroll=5m&pretty" -d '{
  "size": 2,
  "query": {
      "match" : {
          "name" : "John Simon"
      }
  }
}'

# Take the second page
curl -XPOST "$ES_URL/_search/scroll?pretty" -d '{
  "scroll" : "5m",
  "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAA"
}'

# See how much search contexts are open
curl -XGET "$ES_URL/_nodes/stats/indices/search?pretty"

# Delete single search context
curl -XDELETE "$ES_URL/_search/scroll?pretty" -d '{
  "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAA"
}'

# Delete several search contexts
curl -XDELETE "$ES_URL/_search/scroll?pretty" -d '{
  "scroll_id" : [
    "DXF1ZXJ5QW5kRmV0Y2gBAAAAA",
    "DnF1ZXJ5VGhlbkZldGNoBQAAA"
    ]
}'

# Delete all search contexts
curl -XDELETE "$ES_URL/_search/scroll/_all?pretty"
```
