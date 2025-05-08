from decimal import Decimal


def test_create_decimal():
    d: Decimal = Decimal(1.1)
    print(d)
