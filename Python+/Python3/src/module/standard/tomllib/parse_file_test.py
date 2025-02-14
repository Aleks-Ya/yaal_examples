import tomllib
import os
from pathlib import Path


def test_parse_file():
    file: str = os.path.join(os.path.dirname(__file__), "data.toml")
    with open(file, "rb") as toml_file:
        data: dict[str, any] = tomllib.load(toml_file)
    assert data == {'server': {'host': '127.0.0.1', 'port': 8080},
                    'database': {'type': 'sqlite', 'name': 'demo.db'}}


def test_parse_file_as_str():
    file: Path = Path(__file__).parent / "data.toml"
    file_content: str = file.read_text()
    data: dict[str, any] = tomllib.loads(file_content)
    assert data == {'server': {'host': '127.0.0.1', 'port': 8080},
                    'database': {'type': 'sqlite', 'name': 'demo.db'}}
