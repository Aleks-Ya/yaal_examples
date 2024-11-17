from dataclasses import asdict, dataclass
from typing import Any


@dataclass
class Address:
    country: str
    city: str


@dataclass
class Person:
    name: str
    addresses: dict[str, Address]


def test_as_dict():
    address1: Address = Address("UK", "London")
    address2: Address = Address("Germany", "Berlin")
    person: Person = Person("John", {"home": address1, "work": address2})
    # noinspection PyTypeChecker
    person_dict: dict[str, Any] = asdict(person)
    assert person_dict == {
        'name': 'John',
        'addresses': {'home': {'city': 'London', 'country': 'UK'},
                      'work': {'city': 'Berlin', 'country': 'Germany'}}
    }
