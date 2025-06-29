from pathlib import Path

from pandas import DataFrame

from temp_helper import TempPath


def test_write_parquet(people_df: DataFrame):
    file: Path = TempPath.temp_path_absent(suffix='.parquet')
    print(file)
    assert not file.exists()
    people_df.to_parquet(file)
    assert file.stat().st_size > 0
