# Upper and lower case

# Upper case
s = 'abc'
u = s.upper()
assert u == 'ABC'

# Lower case
assert 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя'.lower() == 'АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'.lower()

# casefold()
assert 'abcdefghijklmnopqrstuvwxyz'.casefold() == 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.casefold()
assert 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя'.casefold() == 'АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'.casefold()
