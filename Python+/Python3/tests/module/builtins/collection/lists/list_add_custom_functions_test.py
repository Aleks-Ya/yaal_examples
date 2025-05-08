from typing import Generic, TypeVar, Any


class SmartList(list):
    def get_first(self) -> Any:
        return self[0]


T = TypeVar('T')


class TypedSmartList(list, Generic[T]):
    def get_first(self) -> T:
        return self[0]


def test_custom_function():
    smart_list: SmartList = SmartList([1, 2, 10, 3, 4])
    first: int = smart_list.get_first()
    assert first == 1


def test_typed_custom_function():
    int_list: TypedSmartList[int] = TypedSmartList[int]([1, 2, 10, 3, 4])
    first_int: int = int_list.get_first()
    assert first_int == 1

    str_list: TypedSmartList[str] = TypedSmartList[str](["a", "b"])
    first_str: str = str_list.get_first()
    assert first_str == "a"
