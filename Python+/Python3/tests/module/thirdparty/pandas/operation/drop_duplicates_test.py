from pandas import DataFrame


def test_drop_duplicates():
    df: DataFrame = DataFrame({'Name': ['John', 'Mary', 'John']})
    distinct_df: DataFrame = df.drop_duplicates()
    assert distinct_df.to_string() == """   Name\n0  John\n1  Mary"""
