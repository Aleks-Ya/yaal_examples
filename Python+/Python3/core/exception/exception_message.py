# Get exception message

message = None

try:
    raise ZeroDivisionError('never divide by zero!')
except ZeroDivisionError as ex:
    message = ex.args[0]

assert message == 'never divide by zero!'
