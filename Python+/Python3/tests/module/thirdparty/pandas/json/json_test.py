from io import StringIO
from pathlib import Path

import pandas
from pandas import DataFrame

from current_path import get_file_in_current_dir


def test_read_json_file():
    file: Path = get_file_in_current_dir('people.json')
    df: DataFrame = pandas.read_json(file)
    assert df.to_string() == """   name  age\n0  John   30\n1  Mary   25"""


def test_read_json_str():
    json_str: str = """[{"name":"John","age":30},{"name":"Mary","age":25}]"""
    df: DataFrame = pandas.read_json(StringIO(json_str))
    assert df.to_string() == """   name  age\n0  John   30\n1  Mary   25"""
