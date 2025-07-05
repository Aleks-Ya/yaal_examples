from dataclasses import dataclass

from pandas import Series


def test_series():
    series: Series = Series([2, 4, 6])
    print(series)


def test_create_object_series():
    @dataclass
    class Person:
        name: str
        age: int

        def __str__(self):
            return f"{self.name}-{self.age}"

    series: Series = Series([Person("John", 30), Person("Mary", 25)])
    print(series)


def test_transform_series_elements():
    series: Series = Series([2, 4, 6])
    updated_series: Series = series.apply(lambda x: x * 2)
    print(updated_series)
