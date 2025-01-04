import sqlite3
from sqlite3 import Connection, Cursor


def test_creat_function():
    def double_function(x):
        return x * 2

    connection: Connection = sqlite3.connect(':memory:')
    connection.create_function("double_function", 1, double_function)

    cursor: Cursor = connection.cursor()
    cursor.execute("CREATE TABLE example (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)")
    cursor.execute("INSERT INTO example (name, age) VALUES ('Alice', 20)")
    connection.commit()
    cursor.execute("SELECT name, double_function(age) FROM example")
    fetchall: list[any] = cursor.fetchall()
    assert fetchall == [('Alice', 40)]
    connection.close()
