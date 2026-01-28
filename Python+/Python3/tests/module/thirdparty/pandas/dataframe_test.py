from pandas import DataFrame


def test_print_df_schema(people_df: DataFrame):
    people_df.info()


def test_from_dict():
    ages: dict[str, int] = {'John': 30, 'Mary': 25}
    df: DataFrame = DataFrame.from_dict(ages, orient="index").reset_index()
    df.columns = ["Name", "Age"]
    df.info()
    print(df)
