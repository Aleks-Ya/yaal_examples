from pathlib import Path

import pandas as pd
from pandas import DataFrame
from pandas.testing import assert_frame_equal

from current_path import get_file_in_current_dir


def test_read_xlsx_file(people_df: DataFrame):
    file: Path = get_file_in_current_dir('data.xlsx')
    df: DataFrame = pd.read_excel(file)
    assert_frame_equal(df, people_df)
