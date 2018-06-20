import pydevd
import os
pydevd.settrace('172.17.0.1', port=1090, stdoutToServer=True, stderrToServer=True)
print(os.environ['HOME'])
print("Bye!")