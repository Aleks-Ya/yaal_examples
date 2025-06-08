from typing import Any, Hashable

from pandas import DataFrame


def test_get_cell_value(people_df: DataFrame):
    assert people_df['Age'][0] == 30


def test_select_column(people_df: DataFrame):
    name_df: DataFrame = people_df[['Name']]
    assert name_df.to_string() == """   Name\n0  John\n1  Mary"""


def test_explode():
    df: DataFrame = DataFrame([{'languages': ['English', 'French']}, {'languages': ['English', 'German']}])
    explode_df: DataFrame = df.explode('languages')
    assert explode_df.to_string() == """  languages\n0   English\n0    French\n1   English\n1    German"""


def test_to_dict(people_df: DataFrame):
    df_dict: dict[Hashable, Any] = people_df.to_dict()
    assert df_dict == {'Age': {0: 30, 1: 25}, 'Name': {0: 'John', 1: 'Mary'}}
