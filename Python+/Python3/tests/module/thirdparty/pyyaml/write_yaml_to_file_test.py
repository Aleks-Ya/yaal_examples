from pathlib import Path
from textwrap import dedent

import yaml

from temp_helper import TempPath


def test_write_yaml_to_file():
    exp_names: list[str] = yaml.safe_load(dedent("""
    - 'eric'
    - 'justin'
    - 'mary-kate'
    """))

    file: Path = TempPath.temp_path_absent()
    with open(file, 'w') as f:
        yaml.dump(exp_names, f)

    act_names: list[str] = yaml.safe_load(open(file).read())
    assert act_names == exp_names
