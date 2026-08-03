# Claude Code

Docs: https://code.claude.com/docs/

Install: `brew install --cask claude-code`

Help: `claude --help`
Help for command: `claude mcp --help`
Version: `claude --version`

## Start Claude
Start Claude in the auto permission mode: `claude --permission-mode auto`
Start Claude with given default model: `claude --model sonnet`
Execute given prompt and show response: `claude -p "Print current date and time"`

## Commands
Login: `/login`
Exit: `/exit`
Generate `CLAUDE.md`: `/init`
Resume interrupted session: `claude --resume`

### MCP
List configured MCP servers: `claude mcp list`
Show details about an MCP server: `claude mcp get "claude.ai Todoist"`
Add an MCP server:
```bash
claude mcp add-json trakt '{"type":"stdio","command":"uvx","args":["-p", "3.13","--from","git+https://github.com/wwiens/trakt_mcpserver","trakt-mcp"],"env":{"TRAKT_CLIENT_ID":"your_client_id","TRAKT_CLIENT_SECRET":"your_client_secret"}}'
```
Delete an MCP server: `claude mcp remove trakt`