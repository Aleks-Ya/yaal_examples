from argparse import ArgumentParser, Namespace


def test_option_args():
    script_args: list[str] = ['-n', 'John', '--age', '30']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('-n', '--name')
    parser.add_argument('-a', '--age', type=int)
    namespace: Namespace = parser.parse_args(script_args)

    name: str = namespace.name
    age: int = namespace.age

    assert name == 'John'
    assert age == 30


def test_choices():
    script_args: list[str] = ['--env', 'prd']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('--env', choices=['dev', 'tst', 'prd'])
    namespace: Namespace = parser.parse_args(script_args)

    env: str = namespace.env

    assert env == 'prd'
