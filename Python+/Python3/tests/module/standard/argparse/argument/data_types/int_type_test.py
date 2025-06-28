from argparse import ArgumentParser, Namespace


def test_int_argument_type():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('--position', type=int)

    args: list[str] = ['--position', '7']
    namespace: Namespace = parser.parse_args(args)

    position: int = namespace.position
    assert position == 7
