# Anthropic CLI

Docs: https://platform.claude.com/docs/en/cli-sdks-libraries/cli/quickstart

Install: `brew install anthropics/tap/ant`
Install completions: 
```shell
ant @completion bash > /tmp/ant
sudo cp /tmp/ant /etc/bash_completion.d/ant
```

## Commands
### Info
Version: `ant --version`

### Authentication
Login: `ant auth login`
Logout: `ant auth logout`
Check login: `ant auth status`
Set Organization: `ant profile set organization_id 778282c9-4c51-45c6-bedd-31821d388746`

### Other
Send a message:
```shell
ant messages create \
  --model claude-opus-4-8 \
  --max-tokens 1024 \
  --message '{role: user, content: "Hello, Claude"}'
```

List models: `ant models list`
