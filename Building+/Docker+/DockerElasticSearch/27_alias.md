#  Alias

## Alias for index
```
# Create an alias
curl -XPUT "$ES_URL/${INDEX_NAME}/_alias/${ALIAS_NAME}"

# Show all aliases for index
curl -XGET "$ES_URL/${INDEX_NAME}/_alias/*"

# Show all aliases by name in all indexes
curl -XGET "$ES_URL/_alias/${ALIAS_NAME}"

# Check the alias exists
curl -HEAD "$ES_URL/_alias/${ALIAS_NAME}"

# Delete the alias
curl -XDELETE "$ES_URL/${INDEX_NAME}/_alias/${ALIAS_NAME}"
```
