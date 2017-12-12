# Multi Search

```
curl -XGET $ES_URL/_msearch?pretty -d '
{ "index": "'"$INDEX_NAME"'" }
{"query" : {"match_all" : {}}, "from" : 0, "size" : 10}
{}
'
```
