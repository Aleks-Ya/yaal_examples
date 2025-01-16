class MyClass:
    i = 12345

    @staticmethod
    def f():
        return 'hello world'


def test_static_attribute():
    assert MyClass.i == 12345


def test_static_method():
    assert MyClass.f() == 'hello world'
