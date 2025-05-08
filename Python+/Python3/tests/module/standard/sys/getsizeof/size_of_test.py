import sys


def test_get_size_of():
    assert sys.getsizeof("a") == 42
