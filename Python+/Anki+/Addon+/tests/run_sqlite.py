import sqlite3

connection = sqlite3.connect(':memory:')
cursor = connection.cursor()
cursor.execute("CREATE TABLE example (id INTEGER PRIMARY KEY, name TEXT)")
cursor.execute("INSERT INTO example (name) VALUES ('Alice')")
connection.commit()
cursor.execute("SELECT * FROM example")
print(cursor.fetchall())
connection.close()
