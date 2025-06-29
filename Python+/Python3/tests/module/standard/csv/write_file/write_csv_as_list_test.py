import csv
from pathlib import Path
from textwrap import dedent

from temp_helper import TempPath


def test_write_csv():
    file: Path = TempPath.temp_path_absent(suffix=".csv")
    print(file)

    with open(file, 'w') as csv_file:
        writer: csv.writer = csv.writer(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL)
        writer.writerow(['id', 'name', 'age'])
        writer.writerow([1, 'John', 30])
        writer.writerow([2, 'Mary', 25])

    assert file.read_text() == dedent('''\
    "id","name","age"
    "1","John","30"
    "2","Mary","25"
    ''')
