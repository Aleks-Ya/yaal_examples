import pandas as pd
from pandas import DataFrame


def test_str_to_datetime(people_df: DataFrame):
    df: DataFrame = DataFrame({'Name': ['John', 'Mary'], 'Login': ['1990-01-20T13:18:40', '2000-02-25T15:25:50']})
    df['Login'] = pd.to_datetime(df['Login'])
    df['Year'] = pd.to_datetime(df['Login']).dt.year
    assert df.to_string() == """   Name               Login  Year\n0  John 1990-01-20 13:18:40  1990\n1  Mary 2000-02-25 15:25:50  2000"""


def test_str_to_date(people_df: DataFrame):
    df: DataFrame = DataFrame({'Name': ['John', 'Mary'], 'Birthday': ['1990-01-20', '2000-02-25']})
    df['BirthDate'] = pd.to_datetime(df['Birthday']).dt.date
    assert df.to_string() == """   Name    Birthday   BirthDate\n0  John  1990-01-20  1990-01-20\n1  Mary  2000-02-25  2000-02-25"""


def test_str_to_month(people_df: DataFrame):
    df: DataFrame = DataFrame({'Name': ['John', 'Mary'], 'Birthday': ['1990-01-20', '2000-02-25']})
    df['Month'] = pd.to_datetime(df['Birthday']).dt.month
    assert df.to_string() == """   Name    Birthday  Month\n0  John  1990-01-20      1\n1  Mary  2000-02-25      2"""
