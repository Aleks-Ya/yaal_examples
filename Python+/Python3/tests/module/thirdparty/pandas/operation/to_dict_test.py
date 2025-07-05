from typing import Any, Hashable

from pandas import DataFrame


def test_to_dict(people_df: DataFrame):
    df_dict: dict[Hashable, Any] = people_df.to_dict()
    assert df_dict == {'Age': {0: 30, 1: 25}, 'Name': {0: 'John', 1: 'Mary'}}
