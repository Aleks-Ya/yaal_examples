from argparse import ArgumentParser, Namespace


# Use flags "-n" and "--age"
def test_parse_args_flags():
    script_args: list[str] = ['-n', 'John', '--age', '30']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('-n', '--name', dest='name')
    parser.add_argument('-a', '--age', type=int, dest='age')

    parsed_args: Namespace = parser.parse_args(script_args)
    name: str = parsed_args.name
    age: int = parsed_args.age

    assert name == 'John'
    assert age == 30


# Without flags
def test_parse_args_positional():
    script_args: list[str] = ['John', '30']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int)

    parsed_args: Namespace = parser.parse_args(script_args)
    name: str = parsed_args.name
    age: int = parsed_args.age

    assert name == 'John'
    assert age == 30


# Default values
def test_parse_args_positional_default():
    script_args: list[str] = ['John']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int, default=30, nargs='?')

    parsed_args: Namespace = parser.parse_args(script_args)
    name: str = parsed_args.name
    age: int = parsed_args.age

    assert name == 'John'
    assert age == 30
