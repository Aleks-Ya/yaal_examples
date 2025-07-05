import textwrap
from io import StringIO
from pathlib import Path

import pandas as pd
from pandas import DataFrame
from pandas.testing import assert_frame_equal

from current_path import get_file_in_current_dir


def test_read_csv_str(people_df: DataFrame):
    data: str = textwrap.dedent("""\
        Name,Age
        John,30
        Mary,25
    """)
    df: DataFrame = pd.read_csv(StringIO(data))
    assert_frame_equal(df, people_df)


def test_read_csv_file(people_df: DataFrame):
    file: Path = get_file_in_current_dir('people.csv')
    df: DataFrame = pd.read_csv(file)
    assert_frame_equal(df, people_df)


def test_read_csv_url():
    url: str = "https://download.mlcc.google.com/mledu-datasets/california_housing_train.csv"
    df: DataFrame = pd.read_csv(filepath_or_buffer=url)
    df.info()
