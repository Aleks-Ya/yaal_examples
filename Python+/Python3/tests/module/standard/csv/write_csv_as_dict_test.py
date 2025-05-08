import csv
import tempfile
from textwrap import dedent


def test_write_csv():
    _, file = tempfile.mkstemp(suffix=".csv")
    print(file)

    with open(file, 'w', newline='') as csv_file:
        id_field: str = 'id'
        name_field: str = 'name'
        age_field: str = 'age'
        fieldnames: list[str] = [id_field, name_field, age_field]
        writer = csv.DictWriter(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL, fieldnames=fieldnames)
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
