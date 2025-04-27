# Run: python3 command_line_arguments.py abc
import sys

script_name: str = sys.argv[0]
assert script_name.endswith('command_line_arguments.py')

arg_1: str = sys.argv[1]
assert arg_1 == 'abc'
