# jq CLI
Parsing JSON in Bash.

Install: `sudo apt install -y jq`

Output without double quotes: `echo '{"f1": "abc"}' | jq -r .f1` -> `abc`
Extract field from JSON object: `echo '{"f1": "abc"}' | jq .f1`
Extract field from JSON file: `jq .f1 /tmp/my.json`
Use arg: `echo '{"name": "Linux", "type": "OS"}' | jq --arg key "name" '.[$key]'` -> `Linux`
Replace field values: `echo '{"name": "John","age":30,"working":true}' | jq '.name = "Mary" | .age = 25'`
Select objects by condition: `echo '[{"name":"John","age":30},{"name":"Mary","age":25}]' | jq '.[] | select(.name=="John")'`
Minify (compress, compact) JSON: `echo '{"f1": "abc"}' | jq -c .`

## Array
Extract fiels from an array: `echo '{"ids":[{"id":"11"},{"id":"22"}]}' | jq .ids[].id`
Map each object of an array: `echo '[{"num": 2}, {"num": 3}]' | jq 'map(.num * 5)'`
Get 1st element of an array: 
	- Option 1: `echo '[{"name":"John"},{"name":"Mary"}]' | jq first`
	- Option 2: `echo '[{"name":"John"},{"name":"Mary"}]' | jq '.[0]'`
