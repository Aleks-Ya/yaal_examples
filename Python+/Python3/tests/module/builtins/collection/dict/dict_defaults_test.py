from collections import defaultdict
from typing import Any

import pytest


def test_default_dict():
    price_dict: dict[Any, int] = defaultdict(lambda: 42)
    price_dict['car'] = 1000
    price_dict['house'] = 5000
    assert price_dict['car'] == 1000
    assert price_dict['house'] == 5000
    assert price_dict['airplane'] == 42


def test_dict_as_default_values():
    default_dict: dict[str, int] = {'car': 1000, 'house': 5000}
    actual_dict: dict[str, int] = {'car': 3000, 'airplane': 8000}
    backed_dict: dict[str, int] = default_dict.copy()
    backed_dict.update(actual_dict)
    assert backed_dict['car'] == 3000
    assert backed_dict['house'] == 5000
    assert backed_dict['airplane'] == 8000


def test_get_or_default():
    d: dict[str, int] = {'car': 1000}
    assert d.get('car', 42) == 1000
    assert d.get('airplane', 42) == 42


def test_chain_of_dicts():
    absent: int = 42
    d1: dict[str, int] = {'car': 1000, 'house': 5000, 'airplane': 10000}
    d2: dict[str, int] = {'car': 2000, 'house': 6000}
    d3: dict[str, int] = {'car': 3000}
    assert (d3.get('car') or d2.get('car')) == 3000
    assert (d3.get('house') or d2.get('house')) == 6000
    assert (d3.get('airplane') or d2.get('airplane') or d1.get('airplane')) == 10000
    assert (d3.get('absent_key') or d2.get('absent_key') or d1.get('absent_key') or absent) == 42


if __name__ == "__main__":
    pytest.main()
