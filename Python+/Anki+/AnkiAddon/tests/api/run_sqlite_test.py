import sqlite3
from sqlite3 import Connection, Cursor


def test_run_sqlite():
    connection: Connection = sqlite3.connect(':memory:')
    cursor: Cursor = connection.cursor()
    cursor.execute("CREATE TABLE example (id INTEGER PRIMARY KEY, name TEXT)")
    cursor.execute("INSERT INTO example (name) VALUES ('Alice')")
    connection.commit()
    cursor.execute("SELECT * FROM example")
    assert cursor.fetchall() == [(1, 'Alice')]
    connection.close()
