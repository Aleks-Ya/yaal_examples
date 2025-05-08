import csv
import os


def test_read_csv():
    file: str = os.path.join(os.path.dirname(__file__), 'people.csv')

    with open(file, newline='') as csv_file:
        reader: csv.reader = csv.reader(csv_file, delimiter=' ', quotechar='|')
        act_content: str = ''
        for row in reader:
            act_content = act_content + ', '.join(row) + "\n"

    print(act_content)

    with open(file) as f:
        exp_content: str = f.read()
    assert act_content == exp_content
