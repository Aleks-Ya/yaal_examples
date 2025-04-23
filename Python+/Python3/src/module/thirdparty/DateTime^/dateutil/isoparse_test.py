from datetime import datetime

from dateutil import parser


def test_parse_datetime():
    start_time: datetime = parser.isoparse("2023-01-01T00:00:00Z")
    print(start_time)
