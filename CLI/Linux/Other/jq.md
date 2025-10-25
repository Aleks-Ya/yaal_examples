# jq CLI
Parsing JSON in Bash.

Install: `sudo apt install -y jq`

## Get
Output without double quotes: `echo '{"f1": "abc"}' | jq -r .f1` -> `abc`
Extract field from JSON object: `echo '{"f1": "abc"}' | jq .f1`
Extract field from JSON file: `jq .f1 /tmp/my.json`
Extract two fields from JSON object: `echo '{"f1": "abc", "f2": "xyz"}' | jq '{a: .f1, b: .f2}'`
Use arg: `echo '{"name": "Linux", "type": "OS"}' | jq --arg key "name" '.[$key]'` -> `Linux`
Select objects by condition: `echo '[{"name":"John","age":30},{"name":"Mary","age":25}]' | jq '.[] | select(.name=="John")'`
Minify (compress, compact) JSON: `echo '{"f1": "abc"}' | jq -c .`

## Update
Replace field value: `echo '{"name": "John","age":30,"working":true}' | jq '.name = "Mary" | .age = 25'`
Replace value in array: `echo '{"people":[{"name": "John","age":30,"working":true}]}' | jq '.people[0].name = "Mary" | .people[0].age = 25'`

## Array
Extract fiels from an array: `echo '{"ids":[{"id":"11"},{"id":"22"}]}' | jq .ids[].id`
Map each object of an array: `echo '[{"num": 2}, {"num": 3}]' | jq 'map(.num * 5)'`
Get 1st element of an array: 
	- Option 1: `echo '[{"name":"John"},{"name":"Mary"}]' | jq first`
	- Option 2: `echo '[{"name":"John"},{"name":"Mary"}]' | jq '.[0]'`
