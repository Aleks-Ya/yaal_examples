# Read fields of a table
import phoenixdb.cursor

conn = phoenixdb.connect('http://localhost:8765', autocommit=True)
cursor = conn.cursor()


def get_fields(table: str, cursor):
    query = f"""select column_name from system.catalog
                where table_schem is NULL and table_name = '{table}' and column_name is not null
                order by ordinal_position
            """
    cursor.execute(query, parameters=())
    resp = cursor.fetchall()
    fields = [i for sublist in resp for i in sublist] if resp is not None else list()
    return fields


table = 'FIELD_NAME_TEST'
id_column = 'id'
username_column = 'username'
cursor.execute(f'DROP TABLE IF EXISTS {table}')
cursor.execute(f'CREATE TABLE {table} ({id_column} INTEGER PRIMARY KEY, {username_column} VARCHAR)')

fields = get_fields(table, cursor)

cursor.close()
conn.close()

assert str(fields) == "['ID', 'USERNAME']"
