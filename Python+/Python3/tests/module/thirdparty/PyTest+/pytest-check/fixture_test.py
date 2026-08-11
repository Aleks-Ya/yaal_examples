from pytest_check import check


def test_multi_failures_fixture_2():
    with check:
        assert 0 > 1
    with check:
        assert 5 in [1, 2, 3]
    with check:
        assert "a" == "b"
    with check:
        assert False
