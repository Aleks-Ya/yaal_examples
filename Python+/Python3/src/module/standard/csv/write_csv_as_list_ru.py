import csv
import tempfile

_, file = tempfile.mkstemp(suffix=".csv")
print(file)

with open(file, 'w', newline='', encoding='utf8') as csv_file:
    writer = csv.writer(csv_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL)
    writer.writerow(['Id', 'Имя', 'Возраст'])
    writer.writerow(['1', 'Иван', '30'])
    writer.writerow(['2', 'Наташа', '25'])

with open(file, encoding='utf8') as f:
    act_content = f.read()

print(act_content)

assert act_content == '''"Id","Имя","Возраст"
"1","Иван","30"
"2","Наташа","25"
'''
