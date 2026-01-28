import pandas.testing as pdt
from pandas import DataFrame, Series


def test_filter_by_int_column(people_df: DataFrame):
    assert people_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""
    age: Series = people_df["Age"]
    df: DataFrame = people_df[age > 25]
    assert df.to_string() == """   Name  Age\n0  John   30"""


def test_filter_not_null_column(people_null_df: DataFrame):
    assert people_null_df.to_string() == """   Name   Age   Male\n0  John  30.0   True\n1  Mary  25.0  False\n2  Mark   NaN   None"""
    age: Series = people_null_df["Age"]
    df: DataFrame = people_null_df[age.notnull()]
    assert df.to_string() == """   Name   Age   Male\n0  John  30.0   True\n1  Mary  25.0  False"""


def test_filter_array_contains_case_insensitive():
    df: DataFrame = DataFrame({'Name': ['John', 'Mary', 'Mark'],
                               'Cities': [['London', 'Berlin'], ['Paris', None], None]})
    query_city: str = "LONDON"
    q: str = query_city.casefold()
    mask: Series = df["Cities"].apply(
        lambda cities: (
            any(city is not None and city.casefold() == q for city in cities)
            if isinstance(cities, list)
            else False
        ))

    act_df: DataFrame = df[mask]
    exp_df: DataFrame = DataFrame({'Name': ['John'],
                                   'Cities': [['London', 'Berlin']]})
    pdt.assert_frame_equal(act_df, exp_df)
