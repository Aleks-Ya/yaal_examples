import pytest
from pandas import DataFrame


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
