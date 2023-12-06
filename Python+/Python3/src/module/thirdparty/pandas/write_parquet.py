import pandas as pd

df = pd.DataFrame({'a': [1, 2, 3], 'b': ['A', 'B', 'C']})
df.to_parquet('outfile.parquet')
