from typing import Optional


def test_not_none_opt():
    not_none_opt: Optional[int] = 2
    assert not_none_opt is not None
    assert not_none_opt * 3 == 6


def test_none_opt():
    none_opt: Optional[int] = None
    assert none_opt is None
