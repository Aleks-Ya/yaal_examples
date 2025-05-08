import timerit


def test_function_as_string():
    for _ in timerit(num=100):
        sum(range(100000))
