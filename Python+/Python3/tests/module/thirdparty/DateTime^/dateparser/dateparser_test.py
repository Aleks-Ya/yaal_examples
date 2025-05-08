from datetime import datetime

import dateparser


def test_parse():
    dt: datetime = dateparser.parse('1 hour ago')
    print(dt)
