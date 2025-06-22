import json
from pathlib import Path
from typing import Any

from temp_helper import TempPath


def test_read_json_file():
    json_file: Path = TempPath.temp_path_absent(suffix='.json')
    json_file.write_text('{"a": {"b": 7}}')
    with open(json_file, 'r') as fp:
        json_dict: dict[str, Any] = json.load(fp)
    assert json_dict == {'a': {'b': 7}}
