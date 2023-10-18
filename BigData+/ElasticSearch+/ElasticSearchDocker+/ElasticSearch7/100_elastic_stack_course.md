# ElasticStack course
Udemy: https://www.udemy.com/elasticsearch-and-elastic-stack-in-depth-and-hands-on

## Setup environment
Description: http://sundog-education.com/elasticsearch/
curl -XPUT $ES_URL/shakespeare --data-binary @shakes-mapping.json
curl -XPOST "$ES_URL/shakespeare/_bulk" --data-binary @shakespeare.json    
curl -XGET "$ES_URL/shakespeare/_search?pretty" -d '{
  "query" : {
    "match_phrase" : {
      "text_entry" : "to be or not to be"
    }
  }
}'
