from dataclasses import dataclass


@dataclass
class Address:
    country: str
    city: str


def test_sorted():
    address1: Address = Address("UK", "London")
    address2: Address = Address("Germany", "Berlin")
    address3: Address = Address("France", "Paris")
    address4: Address = Address("UK", "Manchester")
    address5: Address = Address("UK", "Liverpool")
    addresses: list[Address] = [address1, address2, address3, address4, address5]
    sorted_addresses: list[Address] = sorted(addresses, key=lambda address: (address.country, address.city))
    assert sorted_addresses == [address3, address2, address5, address1, address4]
