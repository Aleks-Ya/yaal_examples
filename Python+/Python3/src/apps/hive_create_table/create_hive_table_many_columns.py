# Generate create script for a Hive table having specified number of columns
import tempfile

schema='the_schema'
column_number = 100
row_number = 10

output_file = tempfile.mkstemp(suffix=".sql")[1]
table_name = f'yablokov_many_columns_{column_number}'
table_full_name=f'{schema}.{table_name}'

drop = f'DROP TABLE IF EXISTS {table_full_name};\n'

column_list = [f'col{column} string' for column in range(column_number)]
column_str = ','.join(column_list)
create = f"CREATE TABLE {table_full_name} (\n" \
         f"{column_str}" \
         f"\n) " \
         f"row format delimited fields terminated by ',' " \
         f"stored as TextFile;\n"

values_list = []
for row in range(row_number):
    row_values = [f"'r{row}c{column}'" for column in range(column_number)]
    row_str = ','.join(row_values)
    values_list.append(row_str)

inserts = [f"INSERT INTO {table_full_name} VALUES({value});" for value in values_list]
insert_str = '\n'.join(inserts)

print(f'Output file: {output_file}')
with open(output_file, 'w') as f:
    f.write(drop)
    f.write(create)
    f.write(insert_str)
