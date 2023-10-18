#  Create index

## Empty index
`curl -XPUT $ES_URL/$PEOPLE_INDEX_NAME?pretty`

## With document size field
### Index doesn't exists
```
curl -XPUT $ES_URL/$PEOPLE_INDEX_NAME?pretty -d '{
  "mappings": {  
    "ages": {
      "_size": {
        "enabled": true
      }
    }
  }
}'
```
### Index already exists
```
curl -XPUT $ES_URL/$PEOPLE_INDEX_NAME/_mapping/$PERSONS_TYPE_NAME?pretty -d '{
      "_size": {
        "enabled": true
      }
}'
```
