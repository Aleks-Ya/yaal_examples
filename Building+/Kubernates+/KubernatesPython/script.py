import time
import sys

n = 1
while(True):
	print(f'Hello, Python Kubernates World #{n}')
	sys.stdout.flush()
	time.sleep(5)
	n += 1