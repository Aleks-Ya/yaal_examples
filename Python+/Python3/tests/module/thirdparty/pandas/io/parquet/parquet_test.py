from pathlib import Path

import pandas.testing as pdt
import pandas
from pandas import DataFrame

from temp_helper import TempPath


def test_write_parquet(people_df: DataFrame):
    file: Path = TempPath.temp_path_absent(suffix='.parquet')
    assert not file.exists()
    people_df.to_parquet(file)
    assert file.stat().st_size > 0


def test_read_parquet(people_df: DataFrame):
    file: Path = TempPath.temp_path_absent(suffix='.parquet')
    people_df.to_parquet(file)
    act_df: DataFrame = pandas.read_parquet(file)
    pdt.assert_frame_equal(act_df, people_df)
