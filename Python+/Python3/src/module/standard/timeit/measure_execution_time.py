import timeit
import unittest
from time import sleep


class TestMeasureExecutionTime(unittest.TestCase):

    def test_measure_time(self):
        execution_time: float = timeit.timeit(TestMeasureExecutionTime._method_under_test, number=1)
        self.assertGreater(execution_time, 0.1)

    def test_measure_time_with_parameters_lambda(self):
        execution_time: float = timeit.timeit(
            lambda: TestMeasureExecutionTime._method_under_test_with_parameters(0.5), number=1)
        self.assertGreater(execution_time, 0.1)

    def test_measure_time_with_parameters_string(self):
        execution_time: float = timeit.timeit(
            'TestMeasureExecutionTime._method_under_test_with_parameters(0.5)', globals=globals(), number=1)
        self.assertGreater(execution_time, 0.1)

    @staticmethod
    def _method_under_test():
        sleep(0.5)

    @staticmethod
    def _method_under_test_with_parameters(duration: float):
        sleep(duration)


if __name__ == '__main__':
    unittest.main()
