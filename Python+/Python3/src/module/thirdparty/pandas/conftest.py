import pytest
from pandas import DataFrame


@pytest.fixture
def people_df() -> DataFrame:
    return DataFrame({'Name': ['John', 'Mary'], 'Age': [30, 25]})
