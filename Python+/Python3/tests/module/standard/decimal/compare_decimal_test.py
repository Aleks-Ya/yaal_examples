from decimal import Decimal


def test_compare_decimal():
    d1: Decimal = Decimal('2.0')
    d2: Decimal = Decimal(2.0)
    d3: Decimal = Decimal(2)
    assert d1 == d2
    assert d2 == d3
