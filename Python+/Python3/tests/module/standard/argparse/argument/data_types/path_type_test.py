from pathlib import Path
from argparse import ArgumentParser, Namespace


def test_path_argument_type():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('--file', type=Path)

    args: list[str] = ['--file', '/tmp/out.txt']
    namespace: Namespace = parser.parse_args(args)

    file: Path = namespace.file
    assert file == Path('/tmp/out.txt')
