# Scroll request

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
