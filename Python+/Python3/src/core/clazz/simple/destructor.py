import gc
from typing import Optional

destructor_invoked: bool = False


class MyClass:
    def __del__(self):
        print('Destructor called')
        global destructor_invoked
        destructor_invoked = True


def test_delete_by_del():
    obj: MyClass = MyClass()
    assert not destructor_invoked
    del obj
    gc.collect()
    assert destructor_invoked


def test_delete_variable_by_assign_none():
    obj: Optional[MyClass] = MyClass()
    assert not destructor_invoked
    obj = None
    gc.collect()
    assert destructor_invoked
