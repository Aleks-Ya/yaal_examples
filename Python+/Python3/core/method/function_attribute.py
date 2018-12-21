# Using function attributes (local variables in methods)

name = 'Petr'


def person():
    person.name = 'John'
    return person.name


assert name == 'Petr'
assert person() == 'John'
