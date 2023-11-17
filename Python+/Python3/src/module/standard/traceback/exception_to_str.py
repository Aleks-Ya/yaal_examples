# Exception to string
import traceback
from typing import List

# In try-except
try:
    raise ZeroDivisionError('never divide by zero!')
except ArithmeticError as e:
    text: str = traceback.format_exc()
    print(f'STACK TRACE printed:\n{text}')

# From Exception object
e: Exception = ZeroDivisionError('never divide by zero!')
format_exception_str: List[str] = traceback.format_exception(e)
print(f'format_exception:\n{format_exception_str}')

# Stacktrace from Exception object
e: Exception = ZeroDivisionError('never divide by zero!')
exc_type, exc_value, exc_traceback = type(e), e, e.__traceback__
stack_trace = ''.join(traceback.format_exception(exc_type, exc_value, exc_traceback))
print(f"Stacktrace:\n{stack_trace}")
