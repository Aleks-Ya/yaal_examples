class MyClass:
    i = 12345

    @staticmethod
    def f():
        return 'hello world'


assert MyClass.i == 12345
assert MyClass.f() == 'hello world'
