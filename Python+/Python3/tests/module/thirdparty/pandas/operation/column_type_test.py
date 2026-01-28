from pandas import DataFrame


def test_astype(people_df: DataFrame):
    assert people_df["Age"].dtype == "int64"
    df: DataFrame = people_df.astype({'Age': str})
    assert df["Age"].dtype == "str"
