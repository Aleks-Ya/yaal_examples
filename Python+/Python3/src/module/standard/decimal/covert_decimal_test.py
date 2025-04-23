from decimal import Decimal

def test_convert_decimal():
    d: Decimal = Decimal('2.01')
    f: float = float(d)
    assert f == float(2.01)
