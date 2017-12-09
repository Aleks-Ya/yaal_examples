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


## Show types
`curl -XGET $ES_URL/$JOIN_INDEX_NAME/_mapping?pretty`

## Delete index
`curl -XDELETE $ES_URL/$JOIN_INDEX_NAME?pretty`
