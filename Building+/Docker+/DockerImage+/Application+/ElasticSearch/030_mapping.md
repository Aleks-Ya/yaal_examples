
#  Mapping (types)

## Get mapping
```
# For index
curl -XGET $ES_URL/$PEOPLE_INDEX_NAME/_mapping?pretty

# For type
curl -XGET $ES_URL/_mapping/$PERSONS_TYPE_NAME?pretty

# For index and type
curl -XGET $ES_URL/$PEOPLE_INDEX_NAME/_mapping/$PERSONS_TYPE_NAME?pretty

# For all indexes and types
curl -XGET $ES_URL/_mapping?pretty
```

## Add mapping (type) to exists index
```
# Single mapping
curl -XPUT $ES_URL/$PEOPLE_INDEX_NAME/_mapping/my_new_mapping?pretty -d '
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
curl -XPUT $ES_URL/$PEOPLE_INDEX_NAME?pretty -d '
{
  "mappings": {
    "'"$PERSONS_TYPE_NAME"'": {
      "properties": {
        "name": {
          "type": "text"
        },
        "email": {
          "type": "keyword"
        },
        "age": {
          "type": "integer"
        },
        "companyId": {
          "type": "keyword"
        }
      }
    },
    "'"$COMPANIES_TYPE_NAME"'": {
      "properties": {
        "title": {
          "type": "text"
        }
      }
    }
  }
}'
```

## Delete mapping
It isn't possbile. Need reindex.
