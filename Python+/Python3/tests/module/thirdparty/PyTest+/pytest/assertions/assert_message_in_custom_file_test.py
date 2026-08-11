from custom_asserts import assert_lists_equal


def test_assert_message_in_custom_file():
    assert_lists_equal(["a", "b"], ["a", "c"])
