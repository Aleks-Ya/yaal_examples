# Try-Except statement

# Single exception
try:
    x = 1 / 0
    assert False
except ZeroDivisionError as ex:
    print("Exception: ", ex)
finally:
    print("Finally!")


# Several exception types
try:
    x = 1 / 0
    assert False
except (ZeroDivisionError, RuntimeError) as ex:
    print("Exception: ", ex)
finally:
    print("Finally!")

# Multi "expect" clauses
try:
    x = 1 / 0
    assert False
except ZeroDivisionError as ex:
    print("Exception: ", ex)
except FileNotFoundError as ex:
    print("Exception: ", ex)
finally:
    print("Finally!")
