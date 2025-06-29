import csv
from pathlib import Path
from textwrap import dedent

from temp_helper import TempPath


def test_write_csv():
    file: Path = TempPath.temp_path_absent(suffix=".csv")
    print(file)

    with open(file, 'w', encoding='utf8') as csv_file:
        writer: csv.writer = csv.writer(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL)
        writer.writerow(['Id', 'Имя', 'Возраст'])
        writer.writerow(['1', 'Иван', '30'])
        writer.writerow(['2', 'Наташа', '25'])

    assert file.read_text(encoding='utf8') == dedent('''\
    "Id","Имя","Возраст"
    "1","Иван","30"
    "2","Наташа","25"
    ''')
