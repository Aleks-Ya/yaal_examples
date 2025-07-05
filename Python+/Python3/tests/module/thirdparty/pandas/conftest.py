import pytest
from pandas import DataFrame, Series

from module.thirdparty.pandas.data import Person


@pytest.fixture
def people_df() -> DataFrame:
    return DataFrame({'Name': ['John', 'Mary'], 'Age': [30, 25]})


@pytest.fixture
def people_extended_df() -> DataFrame:
    return DataFrame({'Name': ['John', 'Mary'], 'Age': [30, 25], 'Male': [True, False]})


@pytest.fixture
def people_nested_df() -> DataFrame:
    return DataFrame({
        'Name': [
            'John',
            'Mary'
        ],
        'Location': [
            {'Country': 'UK', 'City': 'London'},
            {'Country': 'France', 'City': 'Paris'}
        ]})


@pytest.fixture
def people_nested_series() -> Series:
    return Series([Person("John", 30), Person("Mary", 25)])
