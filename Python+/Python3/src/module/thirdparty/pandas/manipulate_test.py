from pandas import DataFrame


def test_get_cell_value(people_df: DataFrame):
    assert people_df['Age'][0] == 30
