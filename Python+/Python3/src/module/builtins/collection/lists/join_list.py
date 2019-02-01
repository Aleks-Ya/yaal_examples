# Join elements of list to string

string_list = ['abcd', 'efg']
joined = ",".join(string_list)
assert joined == 'abcd,efg'

num_list = '123'
joined = "','".join(num_list)
print(joined)
assert joined == '123'
