from pandas import DataFrame


def test_explode():
    df: DataFrame = DataFrame([{'languages': ['English', 'French']}, {'languages': ['English', 'German']}])
    explode_df: DataFrame = df.explode('languages')
    assert explode_df.to_string() == """  languages\n0   English\n0    French\n1   English\n1    German"""
