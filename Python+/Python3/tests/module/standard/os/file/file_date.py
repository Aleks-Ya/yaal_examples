import os.path
import tempfile
import time

file = tempfile.mkstemp()[1]

print("created 1: %s" % time.ctime(os.path.getctime(file)))
print("last modified 1: %s" % time.ctime(os.path.getmtime(file)))

time.sleep(2)

with open(file, 'w') as f:
    f.write('abc')

print("created 1: %s" % time.ctime(os.path.getctime(file)))
print("last modified 1: %s" % time.ctime(os.path.getmtime(file)))

