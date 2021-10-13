# Run: cat stdin.txt | python3 read_stdin_1.py
import sys

data = sys.stdin.read()
assert data == 'abc\nefg'
