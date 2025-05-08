# Test methods of a class using pytest


class MyClass:
    def __init__(self):
        self.person = 'John'

    @staticmethod
    def greeting():
        return 'hello world'

    def bye(self, exclamation: str):
        return f'Bye, {self.person_name()}{exclamation}'

    def person_name(self):
        return self.person


def test_greeting_via_instance():
    instance = MyClass()
    assert instance.greeting() == 'hello world'


def test_greeting_via_class_method():
    assert MyClass.greeting() == 'hello world'


def test_bye_method():
    instance = MyClass()
    assert instance.bye('...') == 'Bye, John...'
