import csv

file = 'people.csv'

with open(file, newline='') as csv_file:
    reader = csv.reader(csv_file, delimiter=' ', quotechar='|')
    act_content = ''
    for row in reader:
        act_content = act_content + ', '.join(row) + "\n"

print(act_content)

with open(file) as f:
    exp_content = f.read()
assert act_content == exp_content
