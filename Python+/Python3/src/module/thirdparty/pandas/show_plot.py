import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from matplotlib.axes import Axes
from pandas import Series

plt.close("all")
np.random.seed(123456)
ts: Series = pd.Series(np.random.randn(1000), index=pd.date_range("1/1/2000", periods=1000))
ts: Series = ts.cumsum()
plot: Axes = ts.plot()
plt.show()
