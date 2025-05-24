import csv
import dataclasses
import tempfile
from csv import DictWriter
from textwrap import dedent


def test_write_csv():
    _, file = tempfile.mkstemp(suffix=".csv")
    print(file)

    with open(file, 'w', newline='') as csv_file:
        id_field: str = 'id'
        name_field: str = 'name'
        age_field: str = 'age'
        fieldnames: list[str] = [id_field, name_field, age_field]
        writer: DictWriter[str] = csv.DictWriter(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL,
                                                 fieldnames=fieldnames)
        writer.writeheader()
        writer.writerow({id_field: 1, name_field: 'John', age_field: 30})
        writer.writerow({id_field: 2, name_field: 'Mary', age_field: 25})

    with open(file) as f:
        content: str = f.read()
    assert content == dedent('''\
    "id","name","age"
    "1","John","30"
    "2","Mary","25"
    ''')


@dataclasses.dataclass
class Person:
    id: int
    name: str
    age: int


def test_write_csv_dataclass():
    _, file = tempfile.mkstemp(suffix=".csv")
    print(file)

    with open(file, 'w', newline='') as csv_file:
        field_names: list[str] = [field.name for field in dataclasses.fields(Person)]
        persons: list[Person] = [Person(1, 'John', 30), Person(2, 'Mary', 25)]
        writer: DictWriter[str] = csv.DictWriter(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL,
                                                 fieldnames=field_names)
        writer.writeheader()
        for person in persons:
            writer.writerow(dataclasses.asdict(person))

    with open(file) as f:
        content: str = f.read()
    assert content == dedent('''\
    "id","name","age"
    "1","John","30"
    "2","Mary","25"
    ''')
