from io import StringIO
from pathlib import Path
from typing import Optional, Any

import pandas
from pandas import DataFrame
from pandas.io.json import build_table_schema

from current_path import get_file_in_current_dir
from temp_helper import TempPath


def test_read_json_file():
    file: Path = get_file_in_current_dir('people.json')
    df: DataFrame = pandas.read_json(file)
    assert df.to_string() == """   name  age\n0  John   30\n1  Mary   25"""


def test_read_json_str():
    json_str: str = """[{"name":"John","age":30},{"name":"Mary","age":25}]"""
    df: DataFrame = pandas.read_json(StringIO(json_str))
    assert df.to_string() == """   name  age\n0  John   30\n1  Mary   25"""


def test_write_json_str():
    df: DataFrame = DataFrame({'name': ['John', 'Mary'], 'age': [30, 25]})
    json_str: Optional[str] = df.to_json(orient='records')
    assert json_str == """[{"name":"John","age":30},{"name":"Mary","age":25}]"""


def test_write_json_file():
    file: Path = TempPath.temp_path_absent(suffix='.json')
    assert not file.exists()
    df: DataFrame = DataFrame({'name': ['John', 'Mary'], 'age': [30, 25]})
    df.to_json(file, orient='records')
    assert file.exists()
    assert file.stat().st_size > 0


def test_get_json_schema():
    df: DataFrame = DataFrame({'name': ['John', 'Mary'], 'age': [30, 25]})
    schema: dict[str, Any] = build_table_schema(df, index=False)
    assert schema == {'fields': [{'extDtype': 'str', 'name': 'name', 'type': 'string'},
                                 {'name': 'age', 'type': 'integer'}],
                      'pandas_version': '1.4.0'}
