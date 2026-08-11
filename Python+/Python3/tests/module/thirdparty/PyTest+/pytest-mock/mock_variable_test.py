from pytest_mock import MockerFixture

s: str = "abc"


def test_mock_variable(mocker: MockerFixture):
    assert s == "abc"
    fake_value: str = "xyz"
    mocker.patch("mock_variable_test.s", fake_value)
    assert s == fake_value
