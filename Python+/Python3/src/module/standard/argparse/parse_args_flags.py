# Run: parse_args_flags.py -n John --age 30
from argparse import ArgumentParser, Namespace

parser: ArgumentParser = ArgumentParser()
parser.add_argument('-n', '--name', dest='name')
parser.add_argument('-a', '--age', type=int, dest='age')

parsed_args: Namespace = parser.parse_args()
name: str = parsed_args.name
age: int = parsed_args.age

assert name == 'John'
assert age == 30
