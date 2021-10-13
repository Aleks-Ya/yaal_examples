from for_import import import_var, Data

value = f'Hello, {import_var}!'
assert value == 'Hello, Import!'

data = Data()
assert data.get_city() == 'Moscow'
