from pytest import raises
from pytest_mock import MockerFixture


def get_string():
    return "abc"


def test_mock_function(mocker: MockerFixture):
    assert get_string() == "abc"
    fake_return_value: str = "xyz"
    mocker.patch("mock_function_test.get_string", return_value=fake_return_value)
    assert get_string() == fake_return_value


def test_mock_function_throws_exception(mocker: MockerFixture):
    assert get_string() == "abc"
    fake_exception: ConnectionError = ConnectionError("xyz")
    mocker.patch("mock_function_test.get_string", side_effect=fake_exception)
    with raises(ConnectionError, match="xyz"):
        get_string()
