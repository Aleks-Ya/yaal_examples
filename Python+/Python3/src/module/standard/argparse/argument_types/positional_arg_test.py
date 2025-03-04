from argparse import ArgumentParser, Namespace
from typing import Optional


def test_positional_args():
    script_args: list[str] = ['John', '30']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int)
    namespace: Namespace = parser.parse_args(script_args)

    name: str = namespace.name
    age: int = namespace.age

    assert name == 'John'
    assert age == 30


def test_default_values():
    script_args: list[str] = ['John']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int, default=30, nargs='?')
    namespace: Namespace = parser.parse_args(script_args)

    name: str = namespace.name
    age: int = namespace.age

    assert name == 'John'
    assert age == 30


def test_optional_arg():
    script_args: list[str] = ['John']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int, required=False, default=None, nargs='?')
    namespace: Namespace = parser.parse_args(script_args)

    name: str = namespace.name
    age: Optional[int] = namespace.age

    assert name == 'John'
    assert age is None
