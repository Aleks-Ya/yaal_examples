from pandas import DataFrame


def test_add_column(people_df: DataFrame):
    city_df: DataFrame = people_df
    city_df['City'] = 'London'
    assert city_df.to_string() == """   Name  Age    City\n0  John   30  London\n1  Mary   25  London"""
