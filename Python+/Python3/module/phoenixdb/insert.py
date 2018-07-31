import phoenixdb
import phoenixdb.cursor

database_url = 'http://localhost:8765'
conn = phoenixdb.connect(database_url, autocommit=True)

cursor = conn.cursor()
# cursor.execute("CREATE SCHEMA IF NOT EXISTS python3")
cursor.execute("DROP TABLE IF EXISTS users")
cursor.execute("CREATE TABLE users (id INTEGER PRIMARY KEY, username VARCHAR)")
cursor.execute("UPSERT INTO users VALUES (?, ?)", (1, 'admin'))
cursor.execute("SELECT * FROM users")
print(cursor.fetchall())

# cursor = conn.cursor(cursor_factory=phoenixdb.cursor.DictCursor)
# cursor.execute("SELECT * FROM users WHERE id=1")
# one = cursor.fetchone()
# print(one['USERNAME'])
