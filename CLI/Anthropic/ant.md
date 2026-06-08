# Anthropic CLI

Docs: https://platform.claude.com/docs/en/cli-sdks-libraries/cli/quickstart

Install: `brew install anthropics/tap/ant`
Install completions: 
```shell
ant @completion bash > /tmp/ant
sudo cp /tmp/ant /etc/bash_completion.d/ant
```

Version: `ant --version`
Login: `ant auth login`

Send a message:
```shell
ant messages create \
  --model claude-opus-4-8 \
  --max-tokens 1024 \
  --message '{role: user, content: "Hello, Claude"}'
```

List models: `ant models list`
