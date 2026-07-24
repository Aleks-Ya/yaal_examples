# Trakt.tv MCP server

GitHub: https://github.com/wwiens/trakt_mcpserver
Trakt API: https://github.com/trakt/trakt-api

Generate Client ID and Client Secret: https://app.trakt.tv/settings/apps/api

## Run
### Run with MCP Inspector
```shell
npx @modelcontextprotocol/inspector \
	-e TRAKT_CLIENT_ID="<client_id>" \
	-e TRAKT_CLIENT_SECRET="<client_secret>" \
	uvx -p 3.13 --from git+https://github.com/wwiens/trakt_mcpserver trakt-mcp
```

### Add to Claude Desktop
Include in `~/.config/Claude/claude_desktop_config.json`:
```json
{
  "mcpServers": {
    "trakt": {
      "command": "uvx",
      "args": [ "-p", "3.13", "--from", "git+https://github.com/wwiens/trakt_mcpserver", "trakt-mcp" ],
      "env": {
        "TRAKT_CLIENT_ID": "<client_id>",
        "TRAKT_CLIENT_SECRET": "<client_secret>"
      }
    }
  }
}
```

### Add to Claude Code
```shell
claude mcp add-json trakt \
	'{"type":"stdio","command":"uvx","args":["-p", "3.13","--from","git+https://github.com/wwiens/trakt_mcpserver","trakt-mcp"],"env":{"TRAKT_CLIENT_ID":"<client_id>","TRAKT_CLIENT_SECRET":"<client_secret>"}}'
```
1. Add:
```shell
claude mcp add-json trakt \
	'{
	    "type": "stdio",
	    "command": "uvx",
	    "args": [ "-p", "3.13", "--from", "git+https://github.com/wwiens/trakt_mcpserver", "trakt-mcp" ],
	    "env": {
	        "TRAKT_CLIENT_ID": "<client_id>",
	        "TRAKT_CLIENT_SECRET": "<client_secret>"
	    }
	}'
```
2. Verify: `claude mcp get trakt`