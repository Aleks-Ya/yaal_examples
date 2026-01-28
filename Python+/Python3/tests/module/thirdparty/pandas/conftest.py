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
def people_null_df() -> DataFrame:
    return DataFrame({'Name': ['John', 'Mary', 'Mark'], 'Age': [30, 25, None], 'Male': [True, False, None]})


@pytest.fixture
def people_nested_df() -> DataFrame:
    return DataFrame({
        'Name': [
            'John',
            'Mary',
            'Mark'
        ],
        'Location': [
            {'Country': 'UK', 'City': 'London'},
            {'Country': 'France', 'City': 'Paris'},
            None
        ]})


@pytest.fixture
def people_nested_series() -> Series:
    return Series([Person("John", 30), Person("Mary", 25)])
