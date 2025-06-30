from decimal import Decimal


def test_width_and_precision():
    width: int = 10
    precision: int = 4
    value: Decimal = Decimal("12.34567")
    s: str = f"result: {value:{width}.{precision}}"
    assert s == 'result:      12.35'


def test_precision():
    precision: int = 4
    value: Decimal = Decimal("1209876.34567")
    s: str = f"result: {value:.{precision}f}"
    assert s == 'result: 1209876.3457'


def test_full_precision():
    value: Decimal = Decimal("1209876.3456789")
    s: str = f"result: {value:f}"
    assert s == 'result: 1209876.3456789'
