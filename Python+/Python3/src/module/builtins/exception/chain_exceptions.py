# Chain exception (set cause)

try:
    raise ZeroDivisionError('My cause exception')
except ZeroDivisionError as cause:
    raise OSError('My message') from cause
