x = 0
try:
    x = 1 / 0
except ZeroDivisionError as error:
    print(error)
    x = 2
except ValueError as error:
    print(error)
    x = -1
finally:
    x = x * 3

assert x == 6
