# Class has constructor


# Minimal class 1
class MinimalClass1:
    """minimal class 1"""


m1 = MinimalClass1()


# Minimal class 2
class MinimalClass2:
    pass


m2 = MinimalClass2()


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
        self.text = text


x = ArgsConstructorClass('hi!')
assert x.text == 'hi!'
