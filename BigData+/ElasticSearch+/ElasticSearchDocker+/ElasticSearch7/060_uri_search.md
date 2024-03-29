# Search documents by URI Search

## Single index and type search
```
# Get all documents from the type
curl -XGET $ES_URL/$PEOPLE_INDEX_NAME/$PERSONS_TYPE_NAME/_search?pretty

# Search documents by parameter
curl -XGET "$ES_URL/$PEOPLE_INDEX_NAME/$PERSONS_TYPE_NAME/_search?q=name:Max&pretty"
```

## Multi-type search
```
# Throughout all types
curl -XGET "$ES_URL/$PEOPLE_INDEX_NAME/_search?q=name:Max&pretty"

# Throughout specific types
curl -XGET "$ES_URL/$PEOPLE_INDEX_NAME/ages,shakespeare/_search?q=name:Max&pretty"
```

## Multi-index search
```
# Throughout all indexes and all types
curl -XGET "$ES_URL/_all/_search?q=name:Max&pretty"

# Throughout specific indexes and all types
curl -XGET "$ES_URL/people,shakespeare/_search?q=name:Max&pretty"

# Throughout all indexes and specific types
curl -XGET "$ES_URL/_all/ages,shakespeare/_search?q=name:Max&pretty"
```
