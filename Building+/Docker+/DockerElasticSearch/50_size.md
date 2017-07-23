#  Enable size

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
