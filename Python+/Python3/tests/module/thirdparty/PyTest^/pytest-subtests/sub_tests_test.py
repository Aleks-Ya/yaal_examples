from pytest_subtests import SubTests


def test(subtests: SubTests):
    for i in range(5):
        with subtests.test(msg="custom message", i=i):
            assert i % 2 == 0
