from typing import Any

import dpath


def test_read():
    data: dict[str, dict[str, int]] = {"John": {"age": 30}}
    age: Any = dpath.get(data, 'John/age')
    assert age == 30
