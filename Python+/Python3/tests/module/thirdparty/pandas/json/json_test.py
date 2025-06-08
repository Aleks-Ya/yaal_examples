from pathlib import Path

import pandas
from pandas import DataFrame


def test_read_json():
    file: Path = Path(__file__).parent / 'people.json'
    df: DataFrame = pandas.read_json(file)
    assert df.to_string() == """   name  age\n0  John   30\n1  Mary   25"""
