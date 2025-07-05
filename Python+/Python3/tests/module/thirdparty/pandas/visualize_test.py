from textwrap import dedent

from pandas import DataFrame
from pandas.testing import assert_frame_equal


def test_print(people_df: DataFrame):
    s: str = str(people_df)
    assert s == dedent("""   Name  Age\n0  John   30\n1  Mary   25""")


def test_to_string(people_df: DataFrame):
    assert people_df.to_string() == """   Name  Age\n0  John   30\n1  Mary   25"""


def test_assert_frame_equal(people_df: DataFrame):
    people_df_exp: DataFrame = DataFrame.from_dict({'Name': ['John', 'Mary'], 'Age': [30, 25]})
    assert_frame_equal(people_df, people_df_exp)


def test_info(people_df: DataFrame):
    people_df.info()


def test_describe(people_df: DataFrame):
    description: DataFrame = people_df.describe()
    print(description)
