import sys
from argparse import ArgumentParser, Namespace

from _pytest.monkeypatch import MonkeyPatch


# Take args from "sys.argv"
def test_parse_sys(monkeypatch: MonkeyPatch):
    monkeypatch.setattr(sys, 'argv', ['script.py', '-n', 'John', '--age', '30'])

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('-n', '--name', dest='name')
    parser.add_argument('-a', '--age', type=int, dest='age')
    namespace: Namespace = parser.parse_args()

    name: str = namespace.name
    age: int = namespace.age

    assert name == 'John'
    assert age == 30


# Take args from a string list
def test_parse_str_list():
    script_args: list[str] = ['-n', 'John', '--age', '30']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('-n', '--name', dest='name')
    parser.add_argument('-a', '--age', type=int, dest='age')
    namespace: Namespace = parser.parse_args(script_args)

    name: str = namespace.name
    age: int = namespace.age

    assert name == 'John'
    assert age == 30
