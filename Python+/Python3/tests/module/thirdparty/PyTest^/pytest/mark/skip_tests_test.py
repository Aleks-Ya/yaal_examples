import pytest


@pytest.mark.skip
def test_skip_unconditional():
    assert True


@pytest.mark.skip(reason="no reason")
def test_skip_with_reason():
    assert True


def test_normal_1():
    assert True


def test_normal_2():
    assert True
