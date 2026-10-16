# Anki definition map skill

## Usage
Usage (Anki must be running with the AnkiConnect add-on), start `claude` in this folder and run:
```
/create-definitions-map tag:it::security::cryptography
```
The argument is any Anki search query. The result is saved to `out/<slug>/`
(e.g. `out/it-security-cryptography/`): open `map.html` in a browser for the interactive map;
`definitions.md` and `diagram.mmd` contain the definitions and a Mermaid diagram.

## Setup
Add Anki MCP server:
```shell
claude mcp add-json anki-mcp '{"type":"stdio","command":"npx","args":["-y","@ankimcp/anki-mcp-server","--stdio"],"env":{"ANKI_CONNECT_URL":"http://localhost:8765"}}'
```