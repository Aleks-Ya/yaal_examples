# pytest CLI

## Commands
### Info
Help: `pytest --help`
Version: `pytest --version`

### Run
Run all tests (from project root where `tests` dir is nested): `pytest`
Run given Python file: `pytest pytest tests/cache/test_cache.py`

### Markers
Run tests with given marker: `pytest -m fast`
Run tests with several markers: `pytest -m "fast or slow"`
Run tests without marker: `pytest -m "not slow"`
