#  Delete document

## Delete single document
```
export DOCUMENT_ID=1
curl -XDELETE $ES_URL/$INDEX_NAME/$TYPE_NAME/$DOCUMENT_ID?pretty
```

## Bulk delete
```
# (!) the body should ends with \n
curl -XPUT $ES_URL/_bulk?pretty -d '
{ "delete" : { "_index" : "'"$INDEX_NAME"'", "_type" : "'"$TYPE_NAME"'", "_id" : "1" } }
{ "delete" : { "_index" : "'"$INDEX_NAME"'", "_type" : "'"$TYPE_NAME"'", "_id" : "2" } }
'
```
