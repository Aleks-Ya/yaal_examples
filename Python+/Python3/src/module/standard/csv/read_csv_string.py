# Read a CSV string
import csv
from io import StringIO
from typing import Dict, List

file = 'people.csv'
content = '''\
1,John,30
2,Mary,25
'''

with StringIO(content) as csv_file:
    reader: csv.reader = csv.reader(csv_file, delimiter=',')
    result: Dict[str, List[str]] = dict()
    for row in reader:
        result[row[0]] = row

print(result)
exp = {
    '1': ['1', 'John', '30'],
    '2': ['2', 'Mary', '25']
}
assert exp == result
