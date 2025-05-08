# Dealing with environment variables
import os

# Get single variable
print(os.environ['HOME'])

# Get all variables as dict
print(os.environ)

# Set variable
os.environ["NAME"] = "John"
assert os.environ["NAME"] == "John"

# Append PATH
os.environ["PATH"] = f'{os.environ["PATH"]}:/abc'
print(os.environ["PATH"])

# Is variable exist
exists: bool = 'ABSENTS' in os.environ
assert not exists
exists: bool = 'PATH' in os.environ
assert exists

# Delete existing variable
os.environ['ROOM'] = '1'
assert 'ROOM' in os.environ
del os.environ['ROOM']
assert 'ROOM' not in os.environ

# Delete absent variable (throws "KeyError: 'STREET'")
assert 'STREET' not in os.environ
if 'STREET' in os.environ:
    del os.environ['STREET']
assert 'STREET' not in os.environ

# Substitute env variables
expanded_string = os.path.expandvars("Path: $HOME/Documents/$USER")
print(expanded_string)
