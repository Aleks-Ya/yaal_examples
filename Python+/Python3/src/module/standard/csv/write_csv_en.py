import csv
import tempfile

_, file = tempfile.mkstemp(suffix=".csv")
print(file)

with open(file, 'w', newline='') as csv_file:
    writer = csv.writer(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL)
    writer.writerow(['id', 'name', 'age'])
    writer.writerow(['1', 'John', '30'])
    writer.writerow(['2', 'Mary', '25'])

with open(file) as f:
    content = f.read()
assert content == '''"id","name","age"
"1","John","30"
"2","Mary","25"
'''
