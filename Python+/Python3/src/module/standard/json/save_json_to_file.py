# Save JSON to a file
import json
import tempfile
import textwrap


def test_save_json_to_file():
    data: str = '{"a": {"b": 7}}'
    json_obj: dict[str, object] = json.loads(data)

    f: str = tempfile.mkstemp()[1]

    with open(f, 'w') as fp:
        json.dump(json_obj, fp, indent=2)

    with open(f) as fp:
        act_content: str = fp.read()

    assert act_content == textwrap.dedent('''\
    {
      "a": {
        "b": 7
      }
    }''')
