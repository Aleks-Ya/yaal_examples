# Anki Addons

## Setup Python virtual environment
1. Install PyEnv:
    1. Install PyEnv: `brew install pyenv`
    2. Install VirtualEnv: `brew install pyenv-virtualenv`
2. Create virtual environment:
    1. `pyenv install 3.9.18`
    2. `pyenv virtualenv 3.9.18 anki-addons`
3. Install Anki packages
    1. Activate virtual environment: `pyenv activate anki-addons`
    2. Install packages: `pip install -r requirements.txt`

## Symlink in Addons Folder to addon example dir:
Create: `ln -s ~/pr/home/yaal_examples/Python+/Anki+/Addon+/simple_addon ~/.local/share/Anki2/addons21/simple_addon`
List links: `ls ~/.local/share/Anki2/addons21`
Delete: `unlink ~/.local/share/Anki2/addons21/simple_addon`
