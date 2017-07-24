#  Enable size

```
curl -XPOST "$ES_URL/${INDEX_NAME}/_close?pretty"
curl -XPOST "$ES_URL/${INDEX_NAME}/_open?pretty"
```
