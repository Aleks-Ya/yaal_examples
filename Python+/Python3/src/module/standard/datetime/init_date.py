import unittest
import datetime


class TestDateTime(unittest.TestCase):

    def test_time(self):
        time = datetime.time(8, 25, 30)
        self.assertEqual(str(time), '08:25:30')

    def test_date(self):
        date = datetime.date(2018, 12, 25)
        self.assertEqual(str(date), '2018-12-25')

    def test_date_time(self):
        date_time = datetime.datetime(2018, 12, 25, 8, 25, 30)
        self.assertEqual(str(date_time), '2018-12-25 08:25:30')

    def test_date_time_millis(self):
        date_time_millis = datetime.datetime(2018, 12, 25, 8, 25, 30, 500)
        self.assertEqual(str(date_time_millis), '2018-12-25 08:25:30.000500')

    def test_time_delta(self):
        time_delta = datetime.timedelta(seconds=3)
        self.assertEqual(str(time_delta), '0:00:03')

    def test_empty_time_delta(self):
        empty_time_delta = datetime.timedelta()
        self.assertEqual(str(empty_time_delta), '0:00:00')

    def test_today(self):
        today = datetime.date.today()
        self.assertIsNotNone(today)

    def test_now_date_time(self):
        now_date_time = datetime.datetime.now()
        self.assertIsNotNone(now_date_time)

    def test_now_utc(self):
        now_utc = datetime.datetime.utcnow()
        self.assertIsNotNone(now_utc)


if __name__ == '__main__':
    unittest.main()
