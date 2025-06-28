from argparse import ArgumentParser, Namespace


def test_option_args():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('-n', '--name')
    parser.add_argument('-a', '--age', type=int)

    args: list[str] = ['-n', 'John', '--age', '30']
    namespace: Namespace = parser.parse_args(args)

    name: str = namespace.name
    age: int = namespace.age
    assert name == 'John'
    assert age == 30


def test_choices():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('--env', choices=['dev', 'tst', 'prd'])

    args: list[str] = ['--env', 'prd']
    namespace: Namespace = parser.parse_args(args)

    env: str = namespace.env
    assert env == 'prd'


def test_special_symbols_in_arg_names():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('--underscore_symbol')
    parser.add_argument('--dash-symbol')

    args: list[str] = ['--underscore_symbol', 'underscore', '--dash-symbol', 'dash']
    namespace: Namespace = parser.parse_args(args)

    assert namespace.underscore_symbol == 'underscore'
    assert namespace.dash_symbol == 'dash'
