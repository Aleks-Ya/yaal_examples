from typing import Any, Hashable

from pandas import DataFrame


def test_get_cell_value(people_df: DataFrame):
    assert people_df['Age'][0] == 30


def test_select_one_column(people_df: DataFrame):
    name_df: DataFrame = people_df[['Name']]
    assert name_df.to_string() == """   Name\n0  John\n1  Mary"""


def test_select_two_columns(people_extended_df: DataFrame):
    name_df: DataFrame = people_extended_df[['Name', 'Male']]
    assert name_df.to_string() == """   Name   Male\n0  John   True\n1  Mary  False"""


def test_select_nested_column(people_nested_df: DataFrame):
    people_nested_df['City'] = people_nested_df['Location'].apply(lambda loc: loc['City'])
    name_df: DataFrame = people_nested_df[['Name', 'City']]
    assert name_df.to_string() == """   Name    City\n0  John  London\n1  Mary   Paris"""


def test_explode():
    df: DataFrame = DataFrame([{'languages': ['English', 'French']}, {'languages': ['English', 'German']}])
    explode_df: DataFrame = df.explode('languages')
    assert explode_df.to_string() == """  languages\n0   English\n0    French\n1   English\n1    German"""


def test_to_dict(people_df: DataFrame):
    df_dict: dict[Hashable, Any] = people_df.to_dict()
    assert df_dict == {'Age': {0: 30, 1: 25}, 'Name': {0: 'John', 1: 'Mary'}}
