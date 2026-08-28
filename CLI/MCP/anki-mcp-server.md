# Anki MCP Server

GitHub: https://github.com/ankimcp/anki-mcp-server/
Site: https://ankimcp.ai
Docs: https://ankimcp.ai/docs/

## Using `npx`
Run (default URL): `npx -y @ankimcp/anki-mcp-server --stdio`
Run (given URL): `ANKI_CONNECT_URL=http://localhost:8765 npx -y @ankimcp/anki-mcp-server --stdio`
Run MCP Inspector: `npx @modelcontextprotocol/inspector npx -y @ankimcp/anki-mcp-server --stdio`

## Using `npm`
Run:
1. Install Node packages: `npm install @ankimcp/anki-mcp-server @modelcontextprotocol/inspector`
2. Run MCP Inspector: `npm exec -- mcp-inspector anki-mcp-server --stdio`

## Add to Claude Code
Via NPM (MY CHOICE):
1. Install NPM and NPX: `sudo apt install -y npm`
2. Install "AnkiConnect" addon: `2055492159`
3. For  
	1. Claude Caude for `english-anki-skill` is preconfigured in `~/pr/home/yaal_examples/AI+/Claude+/english-anki-skill/.mcp.json`
	2. Verify: `cd ~/pr/home/yaal_examples/AI+/Claude+/english-anki-skill && claude mcp get anki-mcp-server`

Via "Anki MCP Server" addon:
1. Install "Anki MCP Server" addon: `124672614`
2. Add an MCP Connector: `claude mcp add --scope user --transport http anki http://127.0.0.1:3141/`
3. Verify: `claude mcp list`

## Add to Claude Desktop
1. Install "AnkiConnect" addon: `2055492159`
2. Download MCP Bundle from the latest release: https://github.com/ankimcp/anki-mcp-server/releases
3. Install the bundle:
	1. Claude Desktop -> Settings -> Extensions -> Advanced settings -> Install Extension
	2. Choose the bundle file
	3. Install
4. Verify: `Can you see my Anki collection?`
