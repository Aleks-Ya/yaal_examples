from pandas import DataFrame
import pandas.testing as pdt


def test_assert_frame_equal():
    df: DataFrame = DataFrame([{'Name': 'John', 'Age': 30}, {'Name': 'Mary', 'Age': 25}])
    act_df: DataFrame = df[['Age']]
    exp_df: DataFrame = DataFrame({'Age': [30, 25]})
    pdt.assert_frame_equal(act_df, exp_df)
