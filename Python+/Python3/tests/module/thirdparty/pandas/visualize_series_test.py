from textwrap import dedent

from pandas import Series
from pandas._testing import assert_series_equal

from module.thirdparty.pandas.data import Person


def test_print(people_nested_series: Series):
    s: str = str(people_nested_series)
    assert s == dedent("""0    John-30\n1    Mary-25\ndtype: object""")


def test_to_string(people_nested_series: Series):
    assert people_nested_series.to_string() == """0    John-30\n1    Mary-25"""


def test_assert_frame_equal(people_nested_series: Series):
    exp_series: Series = Series([Person("John", 30), Person("Mary", 25)])
    assert_series_equal(people_nested_series, exp_series)


def test_info(people_nested_series: Series):
    people_nested_series.info()


def test_describe(people_nested_series: Series):
    description: Series = people_nested_series.describe()
    print(description)
