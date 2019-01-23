# Using ranges

# Crate a range
r = range(2)
assert list(r) == [0, 1]

r = range(1, 4)
assert list(r) == [1, 2, 3]

r = range(1, 10, 2)
assert list(r) == [1, 3, 5, 7, 9]


# Year range
first_year = 1990
last_year = 1995
year_range = range(first_year, last_year + 1)
assert list(year_range) == [1990, 1991, 1992, 1993, 1994, 1995]
