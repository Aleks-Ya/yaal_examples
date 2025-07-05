from pandas import Series


def test_series():
    series: Series = Series([2, 4, 6])
    print(series)


def test_create_object_series(people_nested_series: Series):
    print(people_nested_series)


def test_transform_series_elements():
    series: Series = Series([2, 4, 6])
    updated_series: Series = series.apply(lambda x: x * 2)
    print(updated_series)
