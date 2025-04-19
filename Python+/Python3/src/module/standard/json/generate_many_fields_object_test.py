# Generating a JSON object having a lot of fields:
# {"f1": 1, "f2": 2, ... }
import json
import tempfile

field_num: int = 100
column_mapping_list: list[dict[str, object]] = []
for num in range(field_num):
    column_name: str = f'column{num}'
    column_mapping_dict: dict[str, object] = {
        'dataFrameColumnName': column_name,
        'tableColumnName': column_name,
        'tableColumnType': 'VARCHAR',
        'checked': True,
        'tableColumnFormat': '255'
    }
    column_mapping_list.append(column_mapping_dict)

out_file: str = tempfile.mkstemp(suffix='.json')[1]
print(f'Output file: "{out_file}"')
with open(out_file, 'w') as fp:
    json.dump(column_mapping_list, fp)
