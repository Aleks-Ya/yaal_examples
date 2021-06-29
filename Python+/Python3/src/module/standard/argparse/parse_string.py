import argparse

parser = argparse.ArgumentParser()
parser.add_argument('-n', '--name', dest='name')
parser.add_argument('-a', '--age', dest='age')

script_args = "-n John -a 30"
parsed_args = parser.parse_args(script_args.split(" "))
name = parsed_args.name
age = parsed_args.age

assert name == 'John'
assert age == '30'
