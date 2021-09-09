import csv
import tempfile

column_number = 100
row_number = 50
add_header = True

column_digit_capacity = len(str(column_number - 1))
row_digit_capacity = len(str(row_number - 1))

_, file = tempfile.mkstemp(suffix=".csv")
print(file)

with open(file, 'w', newline='') as csv_file:
    writer = csv.writer(csv_file)
    if add_header:
        column_name_template = 'col{:0' + str(column_digit_capacity) + '}'
        writer.writerow([column_name_template.format(column) for column in range(column_number)])
    for row in range(row_number):
        row_value_template = 'r{:0' + str(row_digit_capacity) + '}c{:0' + str(column_digit_capacity) + '}'
        row_values = [row_value_template.format(row, column) for column in range(column_number)]
        writer.writerow(row_values)
print('Done.')
