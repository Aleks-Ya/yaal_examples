# jq CLI

Parsing JSON in Bash.

Extract field from JSON string:
```
echo '{"f1": 33}' | jq .f1
```
Extract field from JSON file:
```
jq .f1 /tmp/my.json     #With double quotes
jq -r .f1 /tmp/my.json  #Without double quotes (raw)
```
