import datetime
import unittest

from freezegun import freeze_time


# Freeze time for a pytest style test:

@freeze_time("2012-01-14")
def test():
    assert datetime.datetime.now() == datetime.datetime(2012, 1, 14)


# Or a unittest TestCase - freezes for every test, from the start of setUpClass to the end of tearDownClass

@freeze_time("1955-11-12")
class MyTests(unittest.TestCase):
    def test_the_class(self):
        assert datetime.datetime.now() == datetime.datetime(1955, 11, 12)


# Or any other class - freezes around each callable (may not work in every case)

@freeze_time("2012-01-14")
class Tester(object):
    def test_the_class(self):
        assert datetime.datetime.now() == datetime.datetime(2012, 1, 14)


# Or method decorator, might also pass frozen time object as kwarg

class TestUnitTestMethodDecorator(unittest.TestCase):
    @freeze_time('2013-04-09')
    def test_method_decorator_works_on_unittest(self):
        self.assertEqual(datetime.date(2013, 4, 9), datetime.date.today())

    @freeze_time('2013-04-09', as_kwarg='frozen_time')
    def test_method_decorator_works_on_unittest(self, frozen_time):
        self.assertEqual(datetime.date(2013, 4, 9), datetime.date.today())
        self.assertEqual(datetime.date(2013, 4, 9), frozen_time.time_to_freeze.today())

    @freeze_time('2013-04-09', as_kwarg='hello')
    def test_method_decorator_works_on_unittest(self, **kwargs):
        self.assertEqual(datetime.date(2013, 4, 9), datetime.date.today())
        self.assertEqual(datetime.date(2013, 4, 9), kwargs.get('hello').time_to_freeze.today())
