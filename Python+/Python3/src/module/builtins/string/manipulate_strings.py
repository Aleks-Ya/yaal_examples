# Manipulate strings

# concatenate
a = "a" + 'b'
assert a == 'ab'

# repeat
a = 'ab' * 3
assert a == 'ababab'

# get character
text = 'abcd'
fromBegin = text[1]
fromEnd = text[-1]
assert fromBegin == 'b'
assert fromEnd == 'd'
assert text[0] == text[-0]

# substrings (slicing)
text = 'abcd'

sub = text[1:3]
assert sub == 'bc'

sub = text[:3]
assert sub == 'abc'

sub = text[1:]
assert sub == 'bcd'

sub = text[-2:]
assert sub == 'cd'

