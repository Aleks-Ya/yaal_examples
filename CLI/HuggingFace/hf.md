# HuggingFace CLI

## Info
Docs: https://huggingface.co/docs/huggingface_hub/main/en/guides/cli

## Install
PIP: `pip install -U huggingface-hub`
Brew: `brew install huggingface-cli`

## Commands
Version: `hf version`
Help: `hf --help`
Help about a command: `hf upload-large-folder --help`

Login: `hf auth login`
Who am I: `hf auth whoami`

Upload a folder (small): `hf upload Ya-Alex/anki-addons . --repo-type=dataset`
Upload a folder (large): `hf upload-large-folder Ya-Alex/anki-addons . --repo-type=dataset --num-workers=16`

Clone repo: `git clone https://huggingface.co/datasets/Ya-Alex/anki-addons`
