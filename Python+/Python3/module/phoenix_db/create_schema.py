# Create schema
# (!) NOT WORK
import phoenixdb.cursor

conn = phoenixdb.connect('http://localhost:8765/', autocommit=True)

cursor = conn.cursor(cursor_factory=phoenixdb.cursor.DictCursor)
cursor.execute("CREATE SCHEMA ABC")
# cursor.execute("CREATE SCHEMA IF NOT EXISTS ABC")

cursor.close()
conn.close()
