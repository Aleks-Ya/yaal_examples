from datetime import date, datetime
from textwrap import dedent
from typing import Any

import yaml


def test_dict_to_yaml():
    created_date: date = date(2018, 12, 25)
    updated: datetime = datetime(2025, 6, 20, 8, 25, 30)
    data: dict[str, Any] = {
        'names': ['John', 'Mary'],
        'created': created_date,
        'updated': updated
    }
    yaml_str: str = yaml.dump(data)
    assert yaml_str == dedent("""\
    created: 2018-12-25
    names:
    - John
    - Mary
    updated: 2025-06-20 08:25:30
    """)
