from argparse import ArgumentParser, Namespace


def test_string_argument_type():
    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('--title', type=str)

    args: list[str] = ['--title', 'Watermark']
    namespace: Namespace = parser.parse_args(args)

    title: str = namespace.title
    assert title == 'Watermark'
