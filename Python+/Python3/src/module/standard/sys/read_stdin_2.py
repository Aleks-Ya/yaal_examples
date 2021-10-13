# Run: cat stdin.txt | python3 read_stdin_2.py
import sys

data = ''
for line in sys.stdin:
    data = data + line
assert data == 'abc\nefg'
