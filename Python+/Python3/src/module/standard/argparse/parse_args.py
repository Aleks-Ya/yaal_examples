# Run: parse_args.py -n John --age 30
import argparse
import sys

parser = argparse.ArgumentParser()
parser.add_argument('-n', '--name', dest='name')
parser.add_argument('-a', '--age', dest='age')

script_args = sys.argv[1:]
parsed_args = parser.parse_args(script_args)
name = parsed_args.name
age = parsed_args.age

assert name == 'John'
assert age == '30'
