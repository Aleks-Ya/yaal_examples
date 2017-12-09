# Settings

```
# Show settings for all indexes
curl -XGET $ES_URL/_all/_settings?pretty

# Show settings for concrete index
curl -XGET $ES_URL/$INDEX_NAME/_settings?pretty
```
