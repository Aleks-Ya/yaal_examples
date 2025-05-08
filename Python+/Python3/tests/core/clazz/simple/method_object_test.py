# Method objects (a variable that refers to a function)


class MyClass:
    @staticmethod
    def get_greeting():
        return 'hello world'


def test_get_greeting():
    hello = MyClass.get_greeting
    assert hello() == 'hello world'
