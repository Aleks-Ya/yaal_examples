from pytest import raises


def test_invalid_parameter():
    with raises(ValueError):
        raise ValueError("Length should be positive")
