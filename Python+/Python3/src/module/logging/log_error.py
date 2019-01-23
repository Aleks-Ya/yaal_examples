import logging

try:
    raise ArithmeticError('Hate math')
except ArithmeticError as e:
    logging.exception("We have a %s", "problem")
