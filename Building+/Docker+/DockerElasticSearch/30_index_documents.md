
#  Index documents
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
