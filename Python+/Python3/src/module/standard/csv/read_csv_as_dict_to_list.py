# Read a CSV file as a dictionary Column0->Column[]
import csv
from typing import Dict, List

file = 'people.csv'

with open(file, newline='') as csv_file:
    reader: csv.reader = csv.reader(csv_file, delimiter=',', quotechar='|')
    result: Dict[str, List[str]] = dict()
    for row in reader:
        result[row[0]] = row

print(result)
exp = {
    'id': ['id', 'name', 'age', 'gender'],
    '1': ['1', 'John', '30', 'M'],
    '2': ['2', 'Mary', '25', 'F'],
    '3': ['3', 'Mark', '20', 'M']
}
assert exp == result
