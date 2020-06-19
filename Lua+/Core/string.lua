-- Literal
s1 = 'str1'
s2 = "str2"

-- Concatenation
s3 = 'str31' .. 'str32'
assert(s3 == 'str31str32')

-- To string
b = true
sb = tostring(b)
assert(sb == 'true')
