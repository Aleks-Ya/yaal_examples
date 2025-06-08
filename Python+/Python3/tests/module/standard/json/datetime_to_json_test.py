import json
from datetime import datetime

from pytest import raises


def test_datetime_not_serializable():
    with raises(TypeError, match="Object of type datetime is not JSON serializable"):
        date_time: datetime = datetime(2018, 12, 25, 8, 25, 30)
        json.dumps(date_time)


def test_datetime_serializer():
    date_time: datetime = datetime(2018, 12, 25, 8, 25, 30)
    s: str = json.dumps(date_time, default=default_serializer)
    assert s == '"2018-12-25T08:25:30"'


def default_serializer(obj: object) -> str:
    if isinstance(obj, datetime):
        return obj.isoformat()
    raise TypeError(f"Type {type(obj)} not serializable")
