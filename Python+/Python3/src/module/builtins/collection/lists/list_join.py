# Join elements of list to string

string_list = ['abcd', 'efg']
joined = ",".join(string_list)
assert joined == 'abcd,efg'

num_list = '123'
joined = "','".join(num_list)
assert joined == "1','2','3"

# Join with transformation #1
list3 = ['abcd', 'efg']
joined3 = ",".join(element.upper() for element in list3)
assert joined3 == 'ABCD,EFG'

# Join with transformation #2
list4 = ['ab', 'efg']
joined4 = ",".join([element.upper() for element in list4])
assert joined4 == 'AB,EFG'
