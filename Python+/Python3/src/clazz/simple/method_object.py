# Method objects (a variable that refers to a function)


class MyClass:
    @staticmethod
    def f():
        return 'hello world'


hello = MyClass.f
assert hello() == 'hello world'
