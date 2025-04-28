import unittest
from datetime import datetime
from unittest.mock import patch
import datetime


class TestMockDateTime(unittest.TestCase):
    def test_mock_datetime(self):
        dt: datetime = datetime.datetime(2020, 1, 1)
        with patch('datetime.datetime') as mock_datetime:
            mock_datetime.now.return_value = dt
            assert str(datetime.datetime.now()) == '2020-01-01 00:00:00'


if __name__ == '__main__':
    unittest.main()
