from pandas import DataFrame


def test_get_top_1(people_df: DataFrame):
    assert people_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""

    head_df: DataFrame = people_df.head(1)
    assert head_df.to_string() == """   Name  Age\n0  John   30"""
