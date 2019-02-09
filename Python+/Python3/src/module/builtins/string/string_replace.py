# Replace substring

# Replace all occurrences
s = 'abcb'
u = s.replace("b", "x")
assert u == 'axcx'

# Replace dollar
u = 'a$bc'.replace("$", "\\$")
print(u)
assert u == "a\\$bc"
assert u == "a\$bc"
