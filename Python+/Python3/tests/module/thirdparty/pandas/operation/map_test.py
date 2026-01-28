from pandas import DataFrame
import pandas.testing as pdt


def test_update_value(people_df: DataFrame):
    assert people_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""
    people_df["Name"] = people_df["Name"].str.upper()
    people_df["Age"] = people_df["Age"] * 2
    assert people_df.to_string() == """   Name  Age\n0  JOHN   60\n1  MARY   50"""


def test_update_value_in_place(people_df: DataFrame):
    assert people_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""
    people_df["Name"] = people_df["Name"].str.upper()
    people_df["Age"] *= 2
    assert people_df.to_string() == """   Name  Age\n0  JOHN   60\n1  MARY   50"""


def test_upper_array():
    df: DataFrame = DataFrame({'Name': ['John', 'Mary'], 'Cities': [['London', 'Berlin'], ['Paris']]})
    assert df.to_string() == """   Name            Cities\n0  John  [London, Berlin]\n1  Mary           [Paris]"""
    df['Cities'] = df['Cities'].apply(lambda cities: [city.upper() for city in cities])
    assert df.to_string() == """   Name            Cities\n0  John  [LONDON, BERLIN]\n1  Mary           [PARIS]"""


def test_upper_array_null():
    act_df: DataFrame = DataFrame({'Name': ['John', 'Mary', 'Mark'],
                                   'Cities': [['London', 'Berlin'], ['Paris', None], None]})
    act_df['Cities'] = act_df['Cities'].apply(lambda cities:
                                              [city.upper() if city is not None else None for city in cities]
                                              if cities is not None else None)
    exp_df: DataFrame = DataFrame({'Name': ['John', 'Mary', 'Mark'],
                                   'Cities': [['LONDON', 'BERLIN'], ['PARIS', None], None]})
    pdt.assert_frame_equal(act_df, exp_df)
