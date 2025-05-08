import os
import tempfile

from pandas import DataFrame


def test_write_parquet(people_df: DataFrame):
    _, file = tempfile.mkstemp(suffix='.parquet')
    print(file)
    assert os.path.getsize(file) == 0
    people_df.to_parquet(file)
    assert os.path.getsize(file) > 0
