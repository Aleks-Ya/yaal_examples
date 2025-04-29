from textwrap import dedent

import yaml


def test_parse_yaml_string():
    names_yaml: str = dedent("""
    - 'eric'
    - 'justin'
    - 'mary-kate'
    """)

    names: list[str] = yaml.safe_load(names_yaml)
    assert names == ['eric', 'justin', 'mary-kate']
