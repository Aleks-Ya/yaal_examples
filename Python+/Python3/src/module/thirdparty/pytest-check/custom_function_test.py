from pytest_check import check


@check.check_func
def is_four(a):
    assert a == 4


def test_all_four():
    is_four(1)
    is_four(2)
    is_four(3)
    is_four(4)
