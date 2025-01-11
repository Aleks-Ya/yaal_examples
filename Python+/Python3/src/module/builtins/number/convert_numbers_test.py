def test_float_to_int():
    i: int = int(123.4)
    assert i == 123


def test_non_num_exception():
    success: bool = False
    try:
        n: str = 'abc'
        int(n)
    except ValueError:
        success = True
    assert success
