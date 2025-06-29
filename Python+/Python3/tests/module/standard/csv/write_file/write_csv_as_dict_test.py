from csv import QUOTE_ALL
import dataclasses
from csv import DictWriter
from pathlib import Path
from textwrap import dedent

from temp_helper import TempPath


def test_write_csv():
    file: Path = TempPath.temp_path_absent(suffix=".csv")
    print(file)

    with open(file, 'w') as csv_file:
        id_field: str = 'id'
        name_field: str = 'name'
        age_field: str = 'age'
        fieldnames: list[str] = [id_field, name_field, age_field]
        writer: DictWriter[str] = DictWriter(csv_file, delimiter=',', quotechar='"', quoting=QUOTE_ALL,
                                             fieldnames=fieldnames)
        writer.writeheader()
        writer.writerow({id_field: 1, name_field: 'John', age_field: 30})
        writer.writerow({id_field: 2, name_field: 'Mary', age_field: 25})

    assert file.read_text() == dedent('''\
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
    file: Path = TempPath.temp_path_absent(suffix=".csv")
    print(file)

    with open(file, 'w') as csv_file:
        field_names: list[str] = [field.name for field in dataclasses.fields(Person)]
        persons: list[Person] = [Person(1, 'John', 30), Person(2, 'Mary', 25)]
        writer: DictWriter[str] = DictWriter(csv_file, delimiter=',', quotechar='"', quoting=QUOTE_ALL,
                                             fieldnames=field_names)
        writer.writeheader()
        for person in persons:
            writer.writerow(dataclasses.asdict(person))

    assert file.read_text() == dedent('''\
    "id","name","age"
    "1","John","30"
    "2","Mary","25"
    ''')
