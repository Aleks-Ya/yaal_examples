# Analyzer

## Test analyzer
### `whitespace` analyzer
`curl –XGET "$ES_URL/$PEOPLE_INDEX_NAME/_analyze?analyzer=whitespace&text=testing,Analyzers&pretty"`

## Explain
Use `explain` option.
`curl –XGET "$ES_URL/$PEOPLE_INDEX_NAME/_analyze?analyzer=whitespace&text=testing,Analyzers&pretty&explain"`

## Custom analyzer
### To lower case Analyzer
```
# Create index and "lower_case_analyzer"
export INDEX_NAME=computers
export TYPE_NAME=notebook
export ANALYZER_NAME=lower_case_analyzer
curl -XPUT $ES_URL/$INDEX_NAME?pretty -d '
{
  "settings": {
    "analysis": {
      "analyzer": {
        "'"$ANALYZER_NAME"'": {
          "type":      "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase"
          ]
        }
      }
    }
  }
}'

# Test analyzer
curl –XGET "$ES_URL/$INDEX_NAME/_analyze?analyzer=$ANALYZER_NAME&text=CamelCaseText&pretty&explain"

# Create mappings
curl -XPUT $ES_URL/$INDEX_NAME/_mapping/computer_mapping?pretty -d '
{
  "properties": {
    "model": {
      "type": "text"
    },
    "serial": {
      "type": "text",
      "analyzer": "'"$ANALYZER_NAME"'"
    }
  }
}'

#Show mappings
curl -XGET $ES_URL/$INDEX_NAME/_mapping?pretty

# Index document
curl -XPOST $ES_URL/$INDEX_NAME/$TYPE_NAME/1?pretty -d '{
  "model": "Asus A500",
  "serial": "ABC-123"
}'

# Get document
curl –XGET "$ES_URL/$INDEX_NAME/$TYPE_NAME/1?pretty"
curl -XGET "$ES_URL/$INDEX_NAME/$TYPE_NAME/_search?q=serial:ABC-123&pretty"
curl -XGET "$ES_URL/$INDEX_NAME/$TYPE_NAME/_search?q=serial:abc-123&pretty"

# Delete index
curl -XDELETE $ES_URL/$INDEX_NAME?pretty
```
