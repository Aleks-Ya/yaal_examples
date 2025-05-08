from pympler import asizeof


def test_object_size():
    obj = [1, 2, (3, 4), 'text']
    print(asizeof.asizeof(obj))
