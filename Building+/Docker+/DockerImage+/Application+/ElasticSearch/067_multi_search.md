# Multi Search

```
curl -XGET $ES_URL/_msearch?pretty -d '
{ "index": "'"$PEOPLE_INDEX_NAME"'" }
{"query" : {"match_all" : {}}, "from" : 0, "size" : 10}
{}
'
```
