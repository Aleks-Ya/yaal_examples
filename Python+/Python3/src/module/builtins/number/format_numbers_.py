# Format numbers

# Split thousands with comma
i = 123456.3
f = "{:,}".format(i)
assert f == '123,456.3'

# Split thousands with spaces
i = 123456.3
f = "{:,}".format(i).replace(',', ' ')
assert f == '123 456.3'

# Padding
i = 123
f = "a{:10,}b".format(i)
assert f == 'a       123b'

# Padding with leading zeros
i = 123
f = "a{:010}b".format(i)
assert f == 'a0000000123b'

# Float to percents
i = 0.756
p = round(i * 100)
f = f"{p}%"
assert f == '76%'
