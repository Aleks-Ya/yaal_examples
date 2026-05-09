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
### Python version
List all available versions: `pyenv install -l`
Install specific version: `pyenv install 3.9.18`
Install several versions: `pyenv install 3.12.8 3.13.1`
Uninstall specific version: `pyenv uninstall 3.9.18`
List installed versions: `pyenv versions`
Use version in current shell session: 
  1. `pyenv shell 3.9.18`
  2. `eval "$(pyenv init --path)"`
Use system Python in current shell session: `pyenv shell system`

### Virtual environment
List existing virtual envs: `pyenv virtualenvs`
Create a virtual env: `pyenv virtualenv 3.13.5 anki-addons-dataset`
Delete a virtual env: `pyenv virtualenv-delete anki-addons-dataset`
Activate a virtual env: `pyenv activate anki-addons-dataset`
Deactivate a virtual env: `pyenv deactivate`

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

### No module `_tkinter`
Command: `pyenv install 3.13.5`
Message:
```
Traceback (most recent call last):
  File "<string>", line 1, in <module>
    import tkinter
  File "/home/aleks/.pyenv/versions/3.13.5/lib/python3.13/tkinter/__init__.py", line 38, in <module>
    import _tkinter # If this fails your Python may not be configured for Tk
    ^^^^^^^^^^^^^^^
ModuleNotFoundError: No module named '_tkinter'
WARNING: The Python tkinter extension was not compiled and GUI subsystem has been detected. Missing the Tk toolkit?
```
Solution: 
 1. Install `brew install python-tk`
 2. Reinstall the version: `pyenv uninstall 3.13.5` and `pyenv install 3.13.5`
