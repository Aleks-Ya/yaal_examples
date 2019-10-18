# Get file by class
import inspect


class MyClass:
    pass


full_name = inspect.getfile(MyClass)

assert full_name.endswith('Python3/src/clazz/simple/get_file_by_class.py')
