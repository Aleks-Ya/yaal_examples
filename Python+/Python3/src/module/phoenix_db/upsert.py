# Use UPSERT
import phoenixdb.cursor

conn = phoenixdb.connect('http://localhost:8765', autocommit=True)
cursor = conn.cursor(cursor_factory=phoenixdb.cursor.DictCursor)

table = 'USERS'
id_column = 'id'
username_column = 'username'
cursor.execute(f'DROP TABLE IF EXISTS {table}')
cursor.execute(f'CREATE TABLE {table} ({id_column} INTEGER PRIMARY KEY, {username_column} VARCHAR)')

cursor.execute(f"UPSERT INTO {table} ({id_column}, {username_column}) VALUES (1, 'admin')")
cursor.execute(f"UPSERT INTO {table} ({id_column}, {username_column}) VALUES (2, 'guest')")

cursor.execute(f'SELECT * FROM {table}', parameters=())  # "parameters" can't be None
result = cursor.fetchall()
assert str(result) == "[{'ID': 1, 'USERNAME': 'admin'}, {'ID': 2, 'USERNAME': 'guest'}]"

cursor.close()
conn.close()
