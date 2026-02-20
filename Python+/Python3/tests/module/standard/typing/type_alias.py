from typing import TypeAlias

Age: TypeAlias = int
ListOfAge: TypeAlias = list[Age]
type City = str


def test_simple_type_alias():
    assert Age is int
    assert type(Age) is type(int)
    assert isinstance(Age, type)
    assert issubclass(Age, int)
    assert Age == int
    assert Age != str
    age: Age = 25
    assert age > 18


def test_list_type_alias():
    assert ListOfAge == list[int]
    ages: ListOfAge = [25, 30, 35]
    assert all(isinstance(age, Age) for age in ages)


def test_type_alias_with_class():
    assert City != str
    city: City = "London"
    assert city == "London"
