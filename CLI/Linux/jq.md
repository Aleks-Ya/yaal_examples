# jq CLI
Parsing JSON in Bash.

Output without double quotes: `echo '{"f1": "abc"}' | jq -r .f1` -> `abc`
Extract field from JSON string: `echo '{"f1": "abc"}' | jq .f1`
Extract field from JSON file: `jq .f1 /tmp/my.json`
