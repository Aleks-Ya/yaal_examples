from pandas import DataFrame


def test_sort_by_int_column(people_df: DataFrame):
    assert people_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""

    sorted_asc_df: DataFrame = people_df.sort_values(by=['Age'])
    assert sorted_asc_df.to_string() == """   Name  Age\n1  Mary   25\n0  John   30"""

    sorted_desc_df: DataFrame = people_df.sort_values(by=['Age'], ascending=False)
    assert sorted_desc_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""
