import inspect
from types import FrameType, CodeType


class MyClass:
    def get_method_name(self) -> str:
        current_frame: FrameType = inspect.currentframe()
        code: CodeType = current_frame.f_code
        name: str = code.co_name
        return name


def test_current_method_name():
    my_class: MyClass = MyClass()
    assert my_class.get_method_name() == "get_method_name"
