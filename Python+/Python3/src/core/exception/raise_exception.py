# Raise an exception

# Exception with message
try:
    raise ZeroDivisionError('never divide by zero!')
except ZeroDivisionError as ex:
    print("Exception: ", ex)

# Nested exception
try:
    raise ZeroDivisionError('never divide by zero!')
except ZeroDivisionError as ex:
    print("Exception: ", ex)
