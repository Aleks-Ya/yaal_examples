
#  Mapping
## Get mapping

```
# For index
curl -XGET "$ES_URL/${INDEX_NAME}/_mapping?pretty"

# For type
curl -XGET "$ES_URL/_mapping/${TYPE_NAME}?pretty"

# For index and type
curl -XGET "$ES_URL/${INDEX_NAME}/_mapping/${TYPE_NAME}?pretty"

# For all indexes and types
curl -XGET "$ES_URL/_mapping?pretty"
```
