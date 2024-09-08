# Markers are declared in pytest.ini
import pytest


@pytest.mark.slow
def test_slow_1():
    assert True


@pytest.mark.slow
def test_slow_2():
    assert True


@pytest.mark.fast
def test_fast_1():
    assert True


@pytest.mark.fast
def test_fast_2():
    assert True


def test_normal_1():
    assert True


def test_normal_2():
    assert True
