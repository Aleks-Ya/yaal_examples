# JSONata CLI (Java)

GitHub: https://github.com/dashjoin/jsonata-cli

Install: `brew install dashjoin/tap/jsonata`

## Commands
Help: `jsonata --help`
Version: `jsonata --version`

Example: `echo '{"a":"hello", "b":" world"}' | jsonata '(a & b)'` -> `hello world`
