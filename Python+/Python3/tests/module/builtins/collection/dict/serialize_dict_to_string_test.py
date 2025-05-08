import json

import pytest


@pytest.fixture
def sample_dict():
    return {'one': 1, 'two': 2, 'three': 3}


def test_json_dumps(sample_dict):
    serialized: str = json.dumps(sample_dict)
    assert serialized == '{"one": 1, "two": 2, "three": 3}'


def test_json_loads(sample_dict):
    serialized: str = json.dumps(sample_dict)
    deserialized: object = json.loads(serialized)
    assert deserialized == sample_dict
