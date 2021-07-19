# Dealing with environment variables
import os

# Get single variable
print(os.environ['HOME'])

# Get all variables as dict
print(os.environ)

# Set variable
os.environ["NAME"] = "John"
assert os.environ["NAME"] == "John"
