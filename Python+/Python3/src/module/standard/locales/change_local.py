# Change current local

import locale
locale.setlocale(locale.LC_ALL, '')
f = "{:n}".format(1234567890)
assert f == '1 234 567 890'
