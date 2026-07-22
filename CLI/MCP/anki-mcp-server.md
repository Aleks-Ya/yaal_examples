# Anki MCP Server

GitHub: https://github.com/ankimcp/anki-mcp-server/

## Using `npx`
Run (default URL): `npx -y @ankimcp/anki-mcp-server --stdio`
Run (given URL): `ANKI_CONNECT_URL=http://localhost:8765 npx -y @ankimcp/anki-mcp-server --stdio`
Run MCP Inspector: `npx @modelcontextprotocol/inspector npx -y @ankimcp/anki-mcp-server --stdio`

## Using `npm`
Run:
1. Install Node packages: `npm install @ankimcp/anki-mcp-server @modelcontextprotocol/inspector`
2. Run MCP Inspector: `npm exec -- mcp-inspector anki-mcp-server --stdio`
