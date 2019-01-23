# Covert Decimal numbers
import decimal

d = decimal.Decimal('2.01')
f = float(d)
assert f == float(2.01)
