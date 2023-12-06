import pandas as pd

from fastparquet import write

df = pd.DataFrame({'a': [1, 2, 3], 'b': ['A', 'B', 'C']})
write('outfile.parquet', df)
