# Formatted strings

import decimal
from datetime import datetime

# string
name = 'John'
assert f"Hi {name}!" == 'Hi John!'

# integer
age = 33
assert f"I am {age} years old" == 'I am 33 years old'

# decimal
width = 10
precision = 4
value = decimal.Decimal("12.34567")
s = f"result: {value:{width}.{precision}}"
assert s == 'result:      12.35'

# date
today = datetime(year=2017, month=1, day=27)
s = f"{today:%b %d, %Y}"
assert s == 'Jan 27, 2017'
