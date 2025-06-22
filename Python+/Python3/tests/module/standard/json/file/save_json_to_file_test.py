import json
import textwrap
from pathlib import Path

from temp_helper import TempPath


def test_save_json_to_file():
    json_obj: dict[str, object] = json.loads('{"a": {"b": 7}}')
    json_file: Path = TempPath.temp_path_absent(suffix='.json')
    with open(json_file, 'w') as fp:
        json.dump(json_obj, fp, indent=2)
    assert json_file.read_text() == textwrap.dedent('''\
    {
      "a": {
        "b": 7
      }
    }''')
