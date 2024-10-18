from pytest_check.context_manager import CheckContextManager


def test_multi_failures_fixture(check: CheckContextManager):
    check.greater(0, 1)
    check.is_true(False)
    check.equal("a", "b")
    check.is_in(5, [1, 2, 3])
