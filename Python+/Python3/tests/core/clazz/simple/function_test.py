# Functions


# Function defined outside the class
def min_value(x, y):
    return min(x, x + y)


def test_min_value():
    assert min_value(1, 2) == 1
