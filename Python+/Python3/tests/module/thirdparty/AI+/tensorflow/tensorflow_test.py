import tensorflow as tf
from tensorflow import Operation


def test_tensorflow():
    x: Operation = tf.constant([[1., 2., 3.], [4., 5., 6.]])
    print(x)
    print(x.shape)
    print(x.dtype)
