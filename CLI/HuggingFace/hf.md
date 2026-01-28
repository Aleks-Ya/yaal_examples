# HuggingFace CLI

## Info
Docs: https://huggingface.co/docs/huggingface_hub/main/en/guides/cli

## Install
PIP: `pip install -U huggingface-hub`
Brew: `brew install huggingface-cli`

## Commands
### Help
Version: `hf version`
Help: `hf --help`
Help about a command: `hf upload-large-folder --help`

### Repo
Clone repo: `git clone https://huggingface.co/datasets/Ya-Alex/anki-addons`
Delete a branch in repo: `hf repo branch delete --repo-type=dataset Ya-Alex/anki-addons parquet-versions-2`

### Auth
Login: `hf auth login`
Who am I: `hf auth whoami`

### Repo files
Delete all files before upload: `hf repo-files delete --repo-type=dataset Ya-Alex/anki-addons '*'`

### Upload
Upload a folder: `hf upload --repo-type=dataset Ya-Alex/anki-addons .`
Upload to a Git branch: `hf upload --repo-type=dataset Ya-Alex/anki-addons . --revision=bug/fix1`

### Upload large folder
Upload a folder: `hf upload-large-folder --repo-type=dataset Ya-Alex/anki-addons . --num-workers=16`

### Spaces
List Spaces: `hf spaces ls`
Search Spaces by sub-string: `hf spaces ls --search anki-addons`
Show Space details: `hf spaces info Ya-Alex/anki-addons`

### Cache
List caches: `hf cache ls`
Clear cache for a repo: `hf cache rm dataset/Ya-Alex/anki-addons`
