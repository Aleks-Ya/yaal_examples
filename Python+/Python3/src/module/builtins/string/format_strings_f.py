from datetime import datetime
from decimal import Decimal


def test_string_format():
    name: str = 'John'
    assert f"Hi {name}!" == 'Hi John!'


def test_integer_format():
    age: int = 33
    assert f"I am {age} years old" == 'I am 33 years old'


def test_decimal_format():
    width: int = 10
    precision: int = 4
    value: Decimal = Decimal("12.34567")
    s: str = f"result: {value:{width}.{precision}}"
    assert s == 'result:      12.35'


def test_date_format():
    today: datetime = datetime(year=2017, month=1, day=27)
    s: str = f"{today:%b %d, %Y}"
    assert s == 'Jan 27, 2017'
