from pytest_check import check


def test_multi_failures():
    check.greater(0, 1)
    check.is_true(False)
    check.equal("a", "b")
    check.is_in(5, [1, 2, 3])
