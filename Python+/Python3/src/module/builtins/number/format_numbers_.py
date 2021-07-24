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
print(f)
assert f == 'a       123b'

# Float to percents
i = 0.756
f = round(i * 100)
print(f"{f}%")
