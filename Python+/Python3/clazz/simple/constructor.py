# Class has constructor


# Minimal class
class MyClass:
    """my class"""


m = MyClass()


# No arguments constructor
class NoArgsConstructorClass:
    def __init__(self):
        self.data = 'hello'


x = NoArgsConstructorClass()
assert x.data == 'hello'


# Constructor has arguments
class ArgsConstructorClass:
    def __new__(cls, *args, **kwargs):
        return super(ArgsConstructorClass, cls).__new__(cls)

    def __init__(self, text):
        self.data = text


x = ArgsConstructorClass('hi!')
assert x.data == 'hi!'
