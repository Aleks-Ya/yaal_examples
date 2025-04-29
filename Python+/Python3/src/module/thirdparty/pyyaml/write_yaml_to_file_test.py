import tempfile
from textwrap import dedent

import yaml


def test_write_yaml_to_file():
    exp_names: list[str] = yaml.safe_load(dedent("""
    - 'eric'
    - 'justin'
    - 'mary-kate'
    """))

    _, full_name = tempfile.mkstemp()
    with open(full_name, 'w') as file:
        yaml.dump(exp_names, file)

    act_names: list[str] = yaml.safe_load(open(full_name).read())
    assert act_names == exp_names
