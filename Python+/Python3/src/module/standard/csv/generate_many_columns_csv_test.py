import csv
import tempfile


def test_generate_many_columns_csv():
    column_number: int = 30000
    row_number: int = 50
    add_header: bool = True

    column_digit_capacity: int = len(str(column_number - 1))
    row_digit_capacity: int = len(str(row_number - 1))

    _, file = tempfile.mkstemp(suffix=".csv")
    print(file)

    with open(file, 'w', newline='') as csv_file:
        writer: csv.writer = csv.writer(csv_file)
        if add_header:
            column_name_template = 'col{:0' + str(column_digit_capacity) + '}'
            writer.writerow([column_name_template.format(column) for column in range(column_number)])
        for row in range(row_number):
            row_value_template = 'r{:0' + str(row_digit_capacity) + '}c{:0' + str(column_digit_capacity) + '}'
            row_values = [row_value_template.format(row, column) for column in range(column_number)]
            writer.writerow(row_values)
    print('Done.')
