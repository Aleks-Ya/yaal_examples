import sqlite3
from sqlite3 import Connection, Cursor
from typing import Any


def test_start_in_memory_db():
    connection: Connection = sqlite3.connect(':memory:')
    cursor: Cursor = connection.cursor()
    cursor.execute("CREATE TABLE example (id INTEGER PRIMARY KEY, name TEXT)")
    cursor.execute("INSERT INTO example (name) VALUES ('Alice')")
    connection.commit()
    cursor.execute("SELECT * FROM example")
    result_set: list[Any] = cursor.fetchall()
    assert result_set == [(1, 'Alice')]
    connection.close()
