from argparse import ArgumentParser, Namespace


def test_flag_args():
    script_args: list[str] = ['--debug']

    parser: ArgumentParser = ArgumentParser()
    parser.add_argument('-d', '--debug', action='store_true')
    parser.add_argument('-v', '--verbose', action='store_true')
    namespace: Namespace = parser.parse_args(script_args)

    debug: bool = namespace.debug
    verbose: bool = namespace.verbose

    assert debug is True
    assert verbose is False
