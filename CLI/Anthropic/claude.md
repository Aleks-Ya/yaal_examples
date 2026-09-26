# Claude Code

Docs: https://code.claude.com/docs/

Install: `brew install --cask claude-code`

Help: `claude --help`
Help for command: `claude mcp --help`
Version: `claude --version`

## Start Claude
Start Claude in the auto permission mode: `claude --permission-mode auto`
Start Claude with given default model: `claude --model sonnet`
Start session with given name: `claude -n refactor`
Resume a previous session (by picker): `claude --resume`
Resume a previous session (by name): `claude --resume session1`
Fork a previous session: `claude -r session1 --fork-session`

## Commands
Login: `/login`
Exit: `/exit`
Generate `CLAUDE.md`: `/init`

### Non-interactive mode
Execute given prompt and show response: `claude -p "Print current date and time"`
With system prompt (string): `claude -p --system-prompt "a=2" "a*3"`
With system prompt (file): `claude -p --system-prompt-file sp.txt "a*3"`
Output as JSON: `claude -p "2+3" --output-format json`
Output JSON schema: `claude -p "2+3" --json-schema '{"type":"object","properties":{"value":{"type":"integer"}}}'`
Allow given tools: `claude -p "What's the temperature in Bangkok now?" --allowed-tools "WebSearch,WebFetch,Bash"`

### MCP
List configured MCP servers: `claude mcp list`
Show details about an MCP server: `claude mcp get "claude.ai Todoist"`
Add an MCP server:
```bash
claude mcp add-json trakt '{"type":"stdio","command":"uvx","args":["-p", "3.13","--from","git+https://github.com/wwiens/trakt_mcpserver","trakt-mcp"],"env":{"TRAKT_CLIENT_ID":"your_client_id","TRAKT_CLIENT_SECRET":"your_client_secret"}}'
```
Delete an MCP server: `claude mcp remove trakt`
