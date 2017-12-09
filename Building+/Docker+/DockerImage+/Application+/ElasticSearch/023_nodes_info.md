#  Nodes info

## Get noted info
```
# All info
curl -XGET $ES_URL/_nodes?pretty

# Installed plugins
curl -XGET $ES_URL/_nodes/plugins?pretty
```
