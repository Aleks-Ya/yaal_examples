import numpy
from numpy import ndarray


def test_normal_distribution():
    num_count: int = 50
    nums: ndarray = numpy.random.normal(5.0, 1.0, num_count)
    assert len(nums) == num_count
    assert 4.0 <= nums.mean() <= 6.0
