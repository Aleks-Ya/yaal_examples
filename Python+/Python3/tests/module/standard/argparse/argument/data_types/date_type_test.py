from argparse import ArgumentParser, Namespace, ArgumentTypeError
from datetime import datetime, date


def valid_date(s: str) -> date:
    try:
        return datetime.strptime(s, "%Y-%m-%d").date()
    except ValueError:
        msg: str = f"Not a valid date: '{s}'. Expected format: YYYY-MM-DD."
        raise ArgumentTypeError(msg)


parser: ArgumentParser = ArgumentParser()
parser.add_argument('--birthday', type=valid_date)


def test_date_argument_type():
    args: list[str] = ['--birthday', '2000-01-31']
    namespace: Namespace = parser.parse_args(args)
    birthday: date = namespace.birthday
    assert birthday == date(2000, 1, 31)
