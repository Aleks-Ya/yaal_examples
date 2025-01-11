def test_digit_check():
    assert '1'.isdigit()
    assert '1234567890'.isdigit()
    assert not '-1'.isdigit()
    assert not '1.1'.isdigit()
    assert not ''.isdigit()
    assert not ' '.isdigit()
