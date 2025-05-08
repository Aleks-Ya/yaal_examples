import sys

print('Write to stdout 1')
sys.stdout.write("Write to stdout 2\n")
sys.stdout.buffer.write(b"some binary data")
