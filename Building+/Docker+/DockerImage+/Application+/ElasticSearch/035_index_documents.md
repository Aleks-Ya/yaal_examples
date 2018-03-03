# Index documents

##  Index single document
### Without ID
```
curl -XPOST $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME?pretty -d '{
  "name": "John",
  "age": 30,
  "email": "john@mail.ru"
}'
```
### With ID
```
curl -XPOST $ES_URL/$INDEX_NAME/$PERSONS_TYPE_NAME/1?pretty -d '{
  "name": "John",
  "age": 30,
  "email": "john@mail.ru"
}'
```

## Bulk index
- (!) The body should end with \n
- "\_id" can be skipped

```
curl -XPUT $ES_URL/_bulk?pretty -d '
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$COMPANIES_TYPE_NAME"'", "_id": "1" } }
{ "title": "Oracle"}
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$COMPANIES_TYPE_NAME"'", "_id": "2" } }
{ "title": "Google"}
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$COMPANIES_TYPE_NAME"'", "_id": "3" } }
{ "title": "Yandex"}
'

curl -XPUT $ES_URL/_bulk?pretty -d '
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$PERSONS_TYPE_NAME"'", "_id": "1" } }
{ "name": "John",  "age": 30, "email": "john@mail.ru", "companyId": "1"}
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$PERSONS_TYPE_NAME"'", "_id": "2" } }
{ "name": "Mary",  "age": 25, "email": "mary@mail.ru", "companyId": "1"}
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$PERSONS_TYPE_NAME"'", "_id": "3" } }
{ "name": "Simon",  "age": 40, "email": "simon@mail.ru", "companyId": "2"}
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$PERSONS_TYPE_NAME"'", "_id": "4" } }
{ "name": "Max",  "age": 20, "email": "max@mail.ru", "companyId": "2"}
{ "index": { "_index": "'"$INDEX_NAME"'", "_type": "'"$PERSONS_TYPE_NAME"'", "_id": "4" } }
{ "name": "Anna",  "age": 35, "email": "anna@mail.ru", "companyId": "3"}
'
```
