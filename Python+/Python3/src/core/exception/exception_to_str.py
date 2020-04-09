# Exception to string

message = 'never divide by zero!'
e = ZeroDivisionError(message)

text = str(e)
assert text == message

# See "traceback" module for printing stack trace
