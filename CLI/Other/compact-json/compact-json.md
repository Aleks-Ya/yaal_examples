# compact-json CLI

## Info
GitHub: https://github.com/masaccio/compact-json

Install: `pip3 install -U compact-json`

## Commands
Help: `compact-json --help`
Version: `compact-json --version`

Compact JSON from string to console: `echo '{"name":"John","age":30}' | compact-json -`
Compact JSON from file to console: `compact-json my.json`
Compact JSON from file to another file: `compact-json in.json -o out.json`
Compact JSON from file to the same file: `compact-json in.json -o in.json`

## Options
### Max Inline Complexity
Docs: https://github.com/j-brooke/FracturedJson/wiki/Options#maxinlinecomplexity
Example 1 (default value `2`): `compact-json max-inline-complexity.json`
Example 2 (value `5`): `compact-json max-inline-complexity.json --max-inline-complexity 6 -l 300`
