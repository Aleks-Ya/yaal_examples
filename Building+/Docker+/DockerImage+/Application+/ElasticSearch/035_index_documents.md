# Index documents

##  Index single document
```
curl -XPOST $ES_URL/$INDEX_NAME/$TYPE_NAME?pretty -d '{
  "name": "John",
  "age": 30,
  "email": "john@mail.ru"
}'
curl -XPOST $ES_URL/$INDEX_NAME/$TYPE_NAME?pretty -d '{
  "name": "Mary",
  "age": 25,
  "email": "mary@mail.ru"
}'
curl -XPOST $ES_URL/$INDEX_NAME/$TYPE_NAME?pretty -d '{
  "name":"Simon",
  "age": 40,
  "email": "simon@mail.ru"
}'
curl -XPOST $ES_URL/$INDEX_NAME/$TYPE_NAME?pretty -d '{
  "name": "Max",
  "age": 20,
  "email": "max@mail.ru"
}'
curl -XPOST $ES_URL/$INDEX_NAME/$TYPE_NAME?pretty -d '{
  "name": "Anna",
  "age": 35,
  "email": "anna@mail.ru"
}'
```

## Bulk index
(!) the body should ends with \n
```
# _id can be skipped
curl -XPUT $ES_URL/_bulk?pretty -d '
{ "index" : { "_index" : "people", "_type" : "ages", "_id" : "1" } }
{ "name":"Mishel",  "age":"20"}
{ "index" : { "_index" : "people", "_type" : "ages", "_id" : "2" } }
{ "name":"James",  "age":"25"}
'
```
