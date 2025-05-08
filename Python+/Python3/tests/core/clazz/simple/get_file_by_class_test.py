# Get file by class
import inspect


class MyClass:
    pass


def test_get_file_by_class():
    full_name: str = inspect.getfile(MyClass)
    assert full_name.endswith('Python3/src/core/clazz/simple/get_file_by_class_test.py')
