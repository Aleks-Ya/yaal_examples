from csv import DictWriter, QUOTE_ALL
from pathlib import Path
from textwrap import dedent
from typing import List

from temp_helper import TempPath


def test_write_csv():
    file: Path = TempPath.temp_path_absent(suffix=".csv")
    print(file)

    id_field: str = 'id'
    name_field: str = 'name'
    age_field: str = 'age'
    fieldnames: List[str] = [id_field, name_field, age_field]

    # New file
    with open(file, 'w') as csv_file:
        writer: DictWriter = DictWriter(csv_file, delimiter=',', quotechar='"', quoting=QUOTE_ALL,
                                        fieldnames=fieldnames)
        writer.writeheader()
        writer.writerow({id_field: 1, name_field: 'John', age_field: 30})
        writer.writerow({id_field: 2, name_field: 'Mary', age_field: 25})

    assert file.read_text() == dedent('''\
    "id","name","age"
    "1","John","30"
    "2","Mary","25"
    ''')

    # Append
    with open(file, 'a') as csv_file:
        writer: DictWriter = DictWriter(csv_file, delimiter=',', quotechar='"', quoting=QUOTE_ALL,
                                        fieldnames=fieldnames)
        writer.writerow({id_field: 3, name_field: 'Nick', age_field: 20})
        writer.writerow({id_field: 4, name_field: 'Ann', age_field: 15})

    assert file.read_text() == dedent('''\
    "id","name","age"
    "1","John","30"
    "2","Mary","25"
    "3","Nick","20"
    "4","Ann","15"
    ''')
