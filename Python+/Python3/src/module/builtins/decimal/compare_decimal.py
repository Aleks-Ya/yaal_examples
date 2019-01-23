# Compare Decimal numbers
import decimal

d1 = decimal.Decimal('2.0')
d2 = decimal.Decimal(2.0)
d3 = decimal.Decimal(2)
assert d1 == d2
assert d2 == d3
