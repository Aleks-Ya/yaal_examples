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
