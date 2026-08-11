def test_custom_assert_message():
    a: int = 0
    b: int = 1
    assert a > b, f"Number {a} is not greater than {b}"
