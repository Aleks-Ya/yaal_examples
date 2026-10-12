# Anki definition map skill

Add Anki MCP server:
```shell
claude mcp add-json anki-mcp '{"type":"stdio","command":"npx","args":["-y","@ankimcp/anki-mcp-server","--stdio"],"env":{"ANKI_CONNECT_URL":"http://localhost:8765"}}'
```
