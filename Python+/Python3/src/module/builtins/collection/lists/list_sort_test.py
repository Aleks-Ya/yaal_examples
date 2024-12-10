def test_sort_ascending():
    l: list[str] = ['a', 'z', 'b', 't']
    l.sort()
    assert l == ['a', 'b', 't', 'z']


def test_sort_descending():
    l: list[str] = ['a', 'z', 'b', 't']
    l.sort(reverse=True)
    assert l == ['z', 't', 'b', 'a']


class Person:
    def __init__(self, name: str, age: int):
        self.name = name
        self.age = age

    def __repr__(self):
        return f'{self.name} - {self.age}'

    def __eq__(self, other):
        return self.name == other.name and self.age == other.age


def test_sort_by_condition():
    original_list: list[Person] = [Person('Mark', 20), Person('John', 40), Person('Mary', 30)]
    sorted_list: list[Person] = sorted(original_list, key=lambda person: person.age)
    assert sorted_list == [Person('Mark', 20), Person('Mary', 30), Person('John', 40)]


def test_sort_by_condition_reversed():
    original_list: list[Person] = [Person('Mark', 20), Person('John', 40), Person('Mary', 30)]
    sorted_list: list[Person] = sorted(original_list, key=lambda person: person.age, reverse=True)
    assert sorted_list == [Person('John', 40), Person('Mary', 30), Person('Mark', 20)]
