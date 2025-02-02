# TOX project with `pyproject.toml` (without `tox.toml`)

## Run unit-tests

1. Using Tox:
    1. Install environments: `pyenv install 3.12.8 3.13.1`
    2. Run normal tests: `tox`
    3. Run integration tests: `tox -- integration`
2. Using PyTest or SetupTools
    1. Prepare virtual environment
        1. Create a virtual environment: `python -m venv venv`
        2. Activate the virtual environment: `source venv/bin/activate`
        3. Install packages: `pip install -r requirements.txt -r requirements-tests.txt`
    2. Run tests
        1. Using `setuptools` (deprecated in favor of PyTest): `python setup.py test`
        2. Using PyTest: `python -m pytest tests`

## Errors

### PyTest-QT tests fail

Command: `tox`
Message: `Fatal Python error: Aborted`
Fix: add `passenv = *` to `tox.ini`