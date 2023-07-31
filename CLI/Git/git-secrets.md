# git-secrets CLI

Install: `sudo apt install -y git-secrets`

List secret patterns: `git secrets --list`
Initialize for repo in current dir: `git secrets --install`
Add standard AWS patterns for all repos: `git secrets --register-aws --global`
Scan working copy in current dir: `git secrets --scan` (files not added to Git are ignored)
