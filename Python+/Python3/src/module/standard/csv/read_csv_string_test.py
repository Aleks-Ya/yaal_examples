# Read a CSV string
import csv
from io import StringIO
from textwrap import dedent
from typing import List


def test_read_csv():
    content: str = dedent('''\
    1,John,30
    2,Mary,25
    ''')

    with StringIO(content) as csv_file:
        reader: csv.reader = csv.reader(csv_file, delimiter=',')
        result: dict[str, List[str]] = dict()
        for row in reader:
            result[row[0]] = row

    print(result)
    assert result == {
        '1': ['1', 'John', '30'],
        '2': ['2', 'Mary', '25']
    }
