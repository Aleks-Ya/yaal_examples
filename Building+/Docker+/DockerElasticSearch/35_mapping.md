
#  Mapping
## Get mapping for specific index and type

```
curl -XGET "$ES_URL/${INDEX_NAME}/_mapping/${TYPE_NAME}?pretty"
```
## Get mapping for all indexes and types

```
curl -XGET "$ES_URL/_mapping?pretty"
```
## Get mapping for specific type

```
curl -XGET "$ES_URL/_mapping/${TYPE_NAME}?pretty"
```
