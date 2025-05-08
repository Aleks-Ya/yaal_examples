import numpy
from numpy import ndarray
from numpy.testing import assert_array_equal


def test_assert_array_equal():
    array1: ndarray = numpy.array([1, 2, 3, 4, 5])
    array2: ndarray = numpy.array([1, 2, 3, 4, 5])
    assert_array_equal(array1, array2)
