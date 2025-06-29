import csv
from pathlib import Path

from current_path import get_file_in_current_dir


def test_read_csv():
    file: Path = get_file_in_current_dir('people.csv')

    with open(file) as csv_file:
        reader: csv.reader = csv.reader(csv_file, delimiter=' ', quotechar='|')
        act_content: str = ''
        for row in reader:
            act_content = act_content + ', '.join(row) + "\n"

    print(act_content)

    with open(file) as f:
        exp_content: str = f.read()
    assert act_content == exp_content
