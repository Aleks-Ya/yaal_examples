import sqlite3
from sqlite3 import Connection, Cursor
from typing import Any


# DOES NOT WORK
def test_regexp():
    connection: Connection = sqlite3.connect(':memory:')
    cursor: Cursor = connection.cursor()
    cursor.execute("SELECT load_extension('regexp');")
    cursor.execute("SELECT 'Hello, World!' REGEXP 'Hello' AS matches;")
    result_set: list[Any] = cursor.fetchall()
    assert result_set == [(1)]
    connection.close()
