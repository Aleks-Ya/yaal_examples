import logging

try:
    raise ArithmeticError('Hate math')
except ArithmeticError as e:
    logging.exception(e)

try:
    raise ArithmeticError('Still hate math')
except ArithmeticError as e:
    logging.exception(e)
