import tomllib
from typing import Any


def test_parse_string():
    toml_string: str = """
    [server]
    host = "127.0.0.1"
    port = 8080
    
    [database]
    type = "sqlite"
    name = "demo.db"
    """
    data: dict[str, Any] = tomllib.loads(toml_string)
    assert data == {'server': {'host': '127.0.0.1', 'port': 8080},
                    'database': {'type': 'sqlite', 'name': 'demo.db'}}
