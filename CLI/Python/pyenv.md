# pyenv CLI

Install: 
1. `brew install pyenv`
2. `pyenv init` and append `~/.bashrc`

## Help
Help: `pyenv help`
Help about a command: `pyenv help install`
Version: `pyenv --version`
Show directory with versions: `echo $(pyenv root)/versions`

## Commands
List all available versions: `pyenv install -l`
Install specific version: `pyenv install 3.9.18`
Install several versions: `pyenv install 3.12.8 3.13.1`
Uninstall specific version: `pyenv uninstall 3.9.18`
List installed versions: `pyenv versions`
Use version in current shell session: `pyenv shell 3.9.18`
Use system Python in current shell session: `pyenv shell system`

## Errors
### No module `_sqlite3`
Command: run `Python+/Python3/src/module/standard/sqlite3/in_memory.py`
Message:
```
Traceback (most recent call last):
  File "/home/aleks/pr/home/yaal_examples/Python+/Python3/src/module/standard/sqlite3/in_memory.py", line 1, in <module>
    import sqlite3
  File "/home/aleks/.pyenv/versions/3.9.18/lib/python3.9/sqlite3/__init__.py", line 57, in <module>
    from sqlite3.dbapi2 import *
  File "/home/aleks/.pyenv/versions/3.9.18/lib/python3.9/sqlite3/dbapi2.py", line 27, in <module>
    from _sqlite3 import *
ModuleNotFoundError: No module named '_sqlite3'

Process finished with exit code 1
```
Solution:
1. Install the dev package: `sudo apt install -y libsqlite3-dev`
2. Uninstall the version: `pyenv uninstall 3.9.18`
3. Install the version: `pyenv install 3.9.18`
