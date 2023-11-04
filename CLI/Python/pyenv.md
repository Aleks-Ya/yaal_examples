# pyenv CLI

Install: `brew install pyenv`

Help: `pyenv help`
Version: `pyenv --version`
Show directory with versions: `echo $(pyenv root)/versions`

List all available versions: `pyenv install -l`
Install specific version: `pyenv install 3.9.15`
Uninstall specific version: `pyenv uninstall 3.9.15`
List installed versions: `pyenv versions`
Use version in current shell session: `pyenv shell 3.9.15`
Use system Python in current shell session: `pyenv shell system`
