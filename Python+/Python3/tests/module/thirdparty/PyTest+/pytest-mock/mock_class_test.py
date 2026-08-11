from unittest.mock import MagicMock

from pytest_mock import MockerFixture


class MyClass:
    def get_string(self):
        return "abc"

    def get_string_with_prefix(self, prefix: str):
        return f"{prefix}: abc"


def test_patch_object(mocker: MockerFixture):
    obj: MyClass = MyClass()
    assert obj.get_string() == "abc"
    fake_return_value: str = "xyz"
    mock_method: MagicMock = mocker.patch.object(MyClass, 'get_string', return_value=fake_return_value)
    assert obj.get_string() == fake_return_value
    mock_method.assert_called_once()


def test_mock_class(mocker: MockerFixture):
    full_name: str = f"{MyClass.__module__}.{MyClass.__qualname__}"
    obj: MyClass = mocker.patch(full_name)
    fake_return_value: str = "xyz"
    mock_method: MagicMock = mocker.patch.object(MyClass, 'get_string', return_value=fake_return_value)
    assert obj.get_string() == fake_return_value
    mock_method.assert_called_once()


def test_patch_method_with_parameter(mocker: MockerFixture):
    obj: MyClass = MyClass()
    assert obj.get_string_with_prefix("hello") == "hello: abc"
    fake_return_value: str = "mocked result"
    mock_method: MagicMock = mocker.patch.object(MyClass, 'get_string_with_prefix', return_value=fake_return_value)
    assert obj.get_string_with_prefix("hello") == fake_return_value
    assert obj.get_string_with_prefix("any prefix") == fake_return_value
    assert mock_method.call_count == 2
    mock_method.assert_any_call("hello")
    mock_method.assert_any_call("any prefix")


def test_patch_method_for_specific_parameter_value(mocker: MockerFixture):
    obj: MyClass = MyClass()
    original_method = MyClass.get_string_with_prefix

    def conditional_mock(prefix: str):
        if prefix == "mock_this":
            return "mocked result"
        else:
            return original_method(obj, prefix)

    mock_method: MagicMock = mocker.patch.object(MyClass, 'get_string_with_prefix', side_effect=conditional_mock)
    assert obj.get_string_with_prefix("mock_this") == "mocked result"
    assert obj.get_string_with_prefix("hello") == "hello: abc"
    assert obj.get_string_with_prefix("test") == "test: abc"
    assert mock_method.call_count == 3
    mock_method.assert_any_call("mock_this")
    mock_method.assert_any_call("hello")
    mock_method.assert_any_call("test")
