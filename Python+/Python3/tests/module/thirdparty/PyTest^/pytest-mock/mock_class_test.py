from pytest_mock import MockerFixture


class MyClass:
    def get_string(self):
        return "abc"


def test_patch_object(mocker: MockerFixture):
    obj: MyClass = MyClass()
    assert obj.get_string() == "abc"
    fake_return_value: str = "xyz"
    mocker.patch.object(MyClass, 'get_string', return_value=fake_return_value)
    assert obj.get_string() == fake_return_value


def test_mock_class(mocker: MockerFixture):
    full_name: str = f"{MyClass.__module__}.{MyClass.__qualname__}"
    obj: MyClass = mocker.patch(full_name)
    fake_return_value: str = "xyz"
    mocker.patch.object(MyClass, 'get_string', return_value=fake_return_value)
    assert obj.get_string() == fake_return_value
