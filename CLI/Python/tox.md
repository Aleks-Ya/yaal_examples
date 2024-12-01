# tox CLI

Install: 
- Via PIX (recommended): `pipx install tox`
- Via PIP: `pip install tox`

Help: `tox --help`
Version: `tox --version`
Verbose: `tox -v`

Execute single test file: `tox -- tests/calculator/test_calculator.py`
Execute several test files: `tox -- tests/test_calculator.py tests/test_formatter.py`
List environments: `tox list`
Re-create environment: `tox -r`
