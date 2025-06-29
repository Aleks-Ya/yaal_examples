from csv import DictReader
from pathlib import Path
from typing import List

from current_path import get_file_in_current_dir


class Person:
    def __init__(self, id: int, name: str, age: int, gender: str):
        self.id = id
        self.name = name
        self.age = age
        self.gender = gender

    def __repr__(self):
        return f"Person[id={self.id}, name={self.name}, age={self.age}, gender={self.gender}]"


def test_read_csv():
    file: Path = get_file_in_current_dir('people.csv')

    with open(file) as csv_file:
        reader: DictReader = DictReader(csv_file)
        persons: List[Person] = []
        for row in reader:
            id: int = int(row['id'])
            name: str = row['name']
            age: int = int(row['age'])
            gender: str = row['gender']
            person = Person(id, name, age, gender)
            persons.append(person)

    print(persons)
    assert str(persons) == '[Person[id=1, name=John, age=30, gender=M], ' \
                           'Person[id=2, name=Mary, age=25, gender=F], ' \
                           'Person[id=3, name=Mark, age=20, gender=M]]'
