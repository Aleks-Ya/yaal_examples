import pandas as pd

from fastparquet import write
from pandas import DataFrame


def test_write_parquet():
    df: DataFrame = pd.DataFrame({'a': [1, 2, 3], 'b': ['A', 'B', 'C']})
    write('outfile.parquet', df)
