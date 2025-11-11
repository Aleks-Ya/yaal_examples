import tomllib
from pathlib import Path
from typing import Any

from current_path import get_file_in_current_dir


def test_parse_file():
    file: Path = get_file_in_current_dir("data.toml")
    with open(file, "rb") as toml_file:
        data: dict[str, Any] = tomllib.load(toml_file)
    assert data == {'server': {'host': '127.0.0.1', 'port': 8080},
                    'database': {'type': 'sqlite', 'name': 'demo.db'}}


def test_parse_file_as_str():
    file: Path = get_file_in_current_dir("data.toml")
    file_content: str = file.read_text()
    data: dict[str, Any] = tomllib.loads(file_content)
    assert data == {'server': {'host': '127.0.0.1', 'port': 8080},
                    'database': {'type': 'sqlite', 'name': 'demo.db'}}
