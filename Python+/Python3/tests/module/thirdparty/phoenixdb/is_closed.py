# Check if connection and cursor closed
import phoenixdb.cursor

conn = phoenixdb.connect('http://localhost:8765', autocommit=True)
assert not conn.closed

cursor = conn.cursor()
assert not cursor.closed

cursor.close()
assert cursor.closed
assert not conn.closed

conn.close()
assert conn.closed
