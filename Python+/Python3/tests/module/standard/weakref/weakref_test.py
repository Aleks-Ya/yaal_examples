import weakref
from _weakref import ReferenceType


def test_weak_ref():
    class A:
        pass

    a: A = A()
    ref: ReferenceType[A] = weakref.ref(a)
    assert ref() is a
    del a
    assert ref() is None
