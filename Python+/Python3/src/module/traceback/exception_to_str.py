# Exception to string
import traceback

e = ZeroDivisionError('never divide by zero!')
text = traceback.format_exc(e)
print(text)
