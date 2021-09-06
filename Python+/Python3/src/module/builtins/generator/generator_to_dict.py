# Generator to Dict

names = ['John', 'Mary']
names_dict = {name: name.upper() for name in names}
assert names_dict == {'John': 'JOHN', 'Mary': 'MARY'}
