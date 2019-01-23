# Return several values from method

# Return the same number of values as expected


def person():
    person.name = 'John'
    person.age = 35
    person.employed = True
    return person.name, person.age, person.employed


name, age, employed = person()

assert name == 'John'
assert age == 35
assert employed


# Number of returned values is not equal to expected
def house():
    house.city = 'SPb'
    house.street = 'Lenina'
    return house.city, house.street


message = None

try:
    city, street, country = house()
except ValueError as ex:
    message = ex.args[0]

assert message == 'not enough values to unpack (expected 3, got 2)'
