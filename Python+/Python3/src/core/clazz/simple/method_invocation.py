# Invoke methods of a class

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


instance = MyClass()
assert instance.greeting() == 'hello world'
assert MyClass.greeting() == 'hello world'
assert instance.bye('...') == 'Bye, John...'
