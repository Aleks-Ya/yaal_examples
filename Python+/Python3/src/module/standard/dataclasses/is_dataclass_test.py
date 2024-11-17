from dataclasses import dataclass, is_dataclass


@dataclass
class Address:
    country: str
    city: str


class Person:
    def __init__(self, name: str, age: int):
        self.name: str = name
        self.age: int = age


def test_is_dataclass():
    address: Address = Address("UK", "London")
    assert is_dataclass(address)

    person: Person = Person("John", 30)
    assert not is_dataclass(person)
