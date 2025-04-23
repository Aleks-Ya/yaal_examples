import pendulum
from pendulum import DateTime


def test_parse_datetime():
    dt: DateTime = pendulum.parse('1975-05-21T22:00:00')
    print(dt)
