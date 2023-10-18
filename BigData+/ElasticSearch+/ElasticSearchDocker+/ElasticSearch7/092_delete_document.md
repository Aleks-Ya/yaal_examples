#  Delete document

## Delete single document
```
export DOCUMENT_ID=1
curl -XDELETE $ES_URL/$PEOPLE_INDEX_NAME/$PERSONS_TYPE_NAME/$DOCUMENT_ID?pretty
```

## Delete all documents in type
```
curl -XPOST $ES_URL/$PEOPLE_INDEX_NAME/$PERSONS_TYPE_NAME/_delete_by_query -d '{
    "query" : {
        "match_all" : {}
    }
}'
```

## Bulk delete
```
# (!) the body should ends with \n
curl -XPUT $ES_URL/_bulk?pretty -d '
{ "delete" : { "_index" : "'"$PEOPLE_INDEX_NAME"'", "_type" : "'"$PERSONS_TYPE_NAME"'", "_id" : "1" } }
{ "delete" : { "_index" : "'"$PEOPLE_INDEX_NAME"'", "_type" : "'"$PERSONS_TYPE_NAME"'", "_id" : "2" } }
'
```
