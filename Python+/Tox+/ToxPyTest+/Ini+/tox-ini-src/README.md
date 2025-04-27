# TOX, PyTest, with `src` dir

## Run unit-tests

1. Using PyTest or SetupTools
    1. Prepare virtual environment
        1. Create a virtual environment: `python -m venv venv`
        2. Activate the virtual environment: `source venv/bin/activate`
        3. Install packages: `pip install -r requirements.txt -r requirements-tests.txt`
    2. Run tests
        1. Using `setuptools` (deprecated in favor of PyTest): `python setup.py test`
        2. Using PyTest: `python -m pytest tests`
2. Using Tox:
    1. Install environment: `pyenv install 3.13.3`
    2. Run normal tests: `tox`
    3. Run integration tests: `tox -e integration`

## Errors

### PyTest-QT tests fail

Command: `tox`
Message: `Fatal Python error: Aborted`
Fix: add `passenv = *` to `tox.ini`