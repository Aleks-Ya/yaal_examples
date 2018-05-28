# Scroll request

## Scroll request
```
# Initial request (get 1st batch and keep context alive for 5 minutes)
curl -XPOST "$ES_URL/$PEOPLE_INDEX_NAME/$PERSONS_TYPE_NAME/_search?scroll=5m&pretty" -d '{
  "size": 2,
  "query": {
      "match" : {
          "name" : "John Simon Max Anna"
      }
  }
}'

# Subsequent query (take 2nd batch and keep context alive for 3 minutes)
curl -XPOST $ES_URL/_search/scroll?pretty -d '{
  "scroll" : "3m",
  "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAA"
}'
```

## Search context
```
# See how much search contexts are open
curl -XGET $ES_URL/_nodes/stats/indices/search?pretty

# Delete single search context
curl -XDELETE $ES_URL/_search/scroll?pretty -d '{
  "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAA"
}'

# Delete several search contexts
curl -XDELETE $ES_URL/_search/scroll?pretty -d '{
  "scroll_id" : [
    "DXF1ZXJ5QW5kRmV0Y2gBAAAAA",
    "DnF1ZXJ5VGhlbkZldGNoBQAAA"
    ]
}'

# Delete all search contexts
curl -XDELETE $ES_URL/_search/scroll/_all?pretty
```
