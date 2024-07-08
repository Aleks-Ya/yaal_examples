import datetime
import unittest
from unittest.mock import patch


class TestGetCurrentDate(unittest.TestCase):

    @patch('datetime.datetime')
    def test_current_date_time(self, datetime_mock):
        tn = datetime.time()
        datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
        expected_date_time = datetime.datetime(2024, 1, 1, 0, 0, 0)
        actual_date_time = datetime.datetime.now()
        self.assertEqual(expected_date_time, actual_date_time)

    @patch('datetime.datetime')
    def test_current_date_time_no_ms(self, datetime_mock):
        datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
        expected_date_time_no_ms = datetime.datetime(2024, 1, 1, 0, 0, 0)
        actual_date_time_no_ms = datetime.datetime.now()
        actual_date_time_no_ms = actual_date_time_no_ms.replace(microsecond=0)
        self.assertEqual(expected_date_time_no_ms, actual_date_time_no_ms)

    @patch('datetime.datetime')
    def test_current_date(self, datetime_mock):
        datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
        expected_date = datetime.datetime(2024, 1, 1, 0, 0, 0).date()
        actual_date = datetime.datetime.now().date()
        self.assertEqual(expected_date, actual_date)

    @patch('datetime.datetime')
    def test_current_year(self, datetime_mock):
        datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
        expected_year = 2024
        actual_year = datetime.datetime.now().date().year
        self.assertEqual(expected_year, actual_year)

    @patch('datetime.datetime')
    def test_current_time(self, datetime_mock):
        datetime_mock.now.return_value = datetime.datetime(2024, 1, 1, 0, 0, 0)
        expected_time = datetime.datetime(2024, 1, 1, 0, 0, 0).time()
        actual_time = datetime.datetime.now().time()
        self.assertEqual(expected_time, actual_time)


if __name__ == '__main__':
    unittest.main()
