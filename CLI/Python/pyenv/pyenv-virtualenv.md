# pyenv-virtualenv

GitHub: https://github.com/pyenv/pyenv-virtualenv

Install: `brew install pyenv-virtualenv`

Help: `pyenv virtualenv --help`
Version: `pyenv virtualenv --version`
Create a virtual environment: `pyenv virtualenv 3.9.18 anki-addons` (path is `/home/aleks/.pyenv/versions/anki-addons/bin/python3`)
Activate a virtual environment for current Bash: `pyenv activate anki-addons`
Deactivate virtual environment: `pyenv deactivate` or `pyenv activate --unset`
Display current active virtual environment: `pyenv version`
List installed virtual environments: 
	- `pyenv versions`
	- `pyenv virtualenvs`
Delete a virtual environment: `pyenv uninstall env1`
