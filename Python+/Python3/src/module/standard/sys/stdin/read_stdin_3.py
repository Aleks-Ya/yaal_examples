# Run: cat stdin.txt | python3 read_stdin_3.py
import fileinput

data: str = ''
for line in fileinput.input():
    data = data + line
assert data == 'abc\nefg'
