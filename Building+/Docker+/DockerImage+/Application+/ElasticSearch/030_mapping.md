
#  Mapping (types)

## Add mapping (type) to exists index
```
# Single mapping
curl -XPUT $ES_URL/$INDEX_NAME/_mapping/my_new_mapping?pretty -d '
{
  "properties": {
    "name": {
      "type": "text"
    },
    "email": {
      "type": "keyword"
    },
    "age": {
      "type": "integer"
    }
  }
}'

# Several mappings (index should not exist)
curl -XPUT $ES_URL/index_several_mappings?pretty -d '
{
  "mappings": {
    "first_mapping": {
      "properties": {
        "message1": {
          "type": "text"
        }
      }
    },
    "second_mapping": {
      "properties": {
        "message2": {
          "type": "text"
        }
      }
    }
  }
}'
```

## Get mapping
```
# For index
curl -XGET $ES_URL/$INDEX_NAME/_mapping?pretty

# For type
curl -XGET $ES_URL/_mapping/$TYPE_NAME?pretty

# For index and type
curl -XGET $ES_URL/$INDEX_NAME/_mapping/$TYPE_NAME?pretty

# For all indexes and types
curl -XGET $ES_URL/_mapping?pretty
```

## Delete mapping
It isn't possbile. Need reindex.
