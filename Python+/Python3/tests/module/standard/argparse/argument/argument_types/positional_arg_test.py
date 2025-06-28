from argparse import ArgumentParser, Namespace
from typing import Optional


def test_positional_args():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int)

    args: list[str] = ['John', '30']
    namespace: Namespace = parser.parse_args(args)

    name: str = namespace.name
    age: int = namespace.age
    assert name == 'John'
    assert age == 30


def test_default_values():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int, default=30, nargs='?')

    args: list[str] = ['John']
    namespace: Namespace = parser.parse_args(args)

    name: str = namespace.name
    age: int = namespace.age
    assert name == 'John'
    assert age == 30


def test_optional_arg():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('name')
    parser.add_argument('age', type=int, default=None, nargs='?')

    args: list[str] = ['John']
    namespace: Namespace = parser.parse_args(args)

    name: str = namespace.name
    age: Optional[int] = namespace.age
    assert name == 'John'
    assert age is None
