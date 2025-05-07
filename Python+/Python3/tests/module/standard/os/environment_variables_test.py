import os

import pytest


@pytest.fixture
def cleanup_env():
    # Store original environment
    original_env: dict[str, str] = dict(os.environ)
    yield
    # Restore original environment
    os.environ.clear()
    os.environ.update(original_env)


def test_get_environment_variable():
    assert 'HOME' in os.environ
    home_path: str = os.environ['HOME']
    assert isinstance(home_path, str)
    assert home_path != ""


def test_get_all_variables():
    all_variables: dict[str, str] = dict(os.environ)
    assert isinstance(all_variables, dict)
    assert len(all_variables) > 0


def test_set_variable(cleanup_env):
    assert 'NAME' not in os.environ
    os.environ["NAME"] = "John"
    assert os.environ["NAME"] == "John"


def test_append_path(cleanup_env):
    original_path: str = os.environ["PATH"]
    os.environ["PATH"] = f'{original_path}:/abc'
    assert os.environ["PATH"] == f'{original_path}:/abc'


def test_variable_exists():
    exists: bool = 'ABSENTS' in os.environ
    assert not exists
    exists = 'PATH' in os.environ
    assert exists


def test_delete_existing_variable(cleanup_env):
    os.environ['ROOM'] = '1'
    assert 'ROOM' in os.environ
    del os.environ['ROOM']
    assert 'ROOM' not in os.environ


def test_delete_absent_variable():
    assert 'STREET' not in os.environ
    if 'STREET' in os.environ:
        del os.environ['STREET']
    assert 'STREET' not in os.environ


def test_expand_variables():
    expanded: str = os.path.expandvars("Path: $HOME/Documents/$USER")
    assert isinstance(expanded, str)
    assert expanded != "Path: $HOME/Documents/$USER"
