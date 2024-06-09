import timeit
import unittest
from time import sleep


class MeasureExecutionTime(unittest.TestCase):

    def test_measure_time(self):
        execution_time: float = timeit.timeit(MeasureExecutionTime._method_under_test, number=1)
        self.assertGreater(execution_time, 0.1)

    @staticmethod
    def _method_under_test():
        sleep(0.5)


if __name__ == '__main__':
    unittest.main()
