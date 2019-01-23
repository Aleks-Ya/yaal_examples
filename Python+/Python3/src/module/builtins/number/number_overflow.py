# Overflow numbers

a: float = 770224622.3
assert a == 770224622.3

b: float = a * 10000000
assert b == 7702246223000000.0

b: float = a * 100000000
assert b == 7.702246223e+16
