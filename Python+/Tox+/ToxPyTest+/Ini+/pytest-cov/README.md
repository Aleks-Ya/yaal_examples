# TOX project with coverage: use `pytest-cov` plugin

## Run unit-tests

Using `setuptools` (deprecated): `python setup.py test`

Using PyTest: `pytest tests`

Using Tox:

1. Install environment: `pyenv install 3.12.7`
2. Run tests: `tox`

## Errors

### PyTest-QT tests fail

Command: `tox`
Message: `Fatal Python error: Aborted`
Fix: add `passenv = *` to `tox.ini`