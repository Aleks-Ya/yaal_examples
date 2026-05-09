# Alias

## Alias for index
### Create alias
```shell
curl -XPUT $ES_URL/$PEOPLE_INDEX_NAME/_alias/$ALIAS_NAME?pretty
```
### Show aliases
```shell
# All aliases for all indexes
curl -XGET $ES_URL/_alias?pretty

# All aliases for index
curl -XGET $ES_URL/$PEOPLE_INDEX_NAME/_alias/*?pretty

# All aliases by name in all indexes
curl -XGET $ES_URL/_alias/$ALIAS_NAME?pretty
```

### Check does the alias exist
```shell
curl -HEAD $ES_URL/_alias/$ALIAS_NAME?pretty
```

### Delete the alias
```shell
curl -XDELETE $ES_URL/$PEOPLE_INDEX_NAME/_alias/$ALIAS_NAME?pretty
```
