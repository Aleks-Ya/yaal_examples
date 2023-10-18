# Join types

## Environment
`export JOIN_INDEX_NAME=join_index`

## Create index
`curl -XPUT $ES_URL/$JOIN_INDEX_NAME`

## Create types
```
curl -XPUT $ES_URL/$JOIN_INDEX_NAME?pretty -d '
{
  "mappings": {
    "dataset-a": {
      "properties": {
        "label-a": {
          "type": "text"
        },
        "dose-a": {
          "type": "double"
        }
      }
    },
    "dataset-b": {
      "properties": {
        "label-b": {
          "type": "text"
        },
        "dose-b": {
          "type": "double"
        }
      }
    },
    "join_dataset-a_label-a_dataset-b_label-b": {
      "properties": {
        "id-a": {
          "type": "text"
        },
        "id-b": {
          "type": "text"
        }
      }
    }
  }
}'
```

## Index documents
```
curl -XPUT $ES_URL/_bulk?pretty -d '
{ "index" : { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "dataset-a", "_id" : "1" } }
{ "label-a":"a1", "dose-a": 1.5}
{ "index" : { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "dataset-a", "_id" : "2" } }
{ "label-a":"a2", "dose-a": 2.5}
{ "index" : { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "dataset-a", "_id" : "3" } }
{ "label-a":"a3", "dose-a": 3.5}

{ "index" : { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "dataset-b", "_id" : "1" } }
{ "label-b":"b1", "dose-b": 1.5}
{ "index" : { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "dataset-b", "_id" : "2" } }
{ "label-b":"b2", "dose-b": 2.5}
{ "index" : { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "dataset-b", "_id" : "3" } }
{ "label-b":"b3", "dose-b": 3.5}

{ "index": { "_index" : "'"$JOIN_INDEX_NAME"'", "_type" : "join_dataset-a_label-a_dataset-b_label-b", "_id" : "1" } }
{ "id-a": "1", "id-b": "[2,3]"}
'
```

## Join search


## Show types
`curl -XGET $ES_URL/$JOIN_INDEX_NAME/_mapping?pretty`

## Delete index
`curl -XDELETE $ES_URL/$JOIN_INDEX_NAME?pretty`
