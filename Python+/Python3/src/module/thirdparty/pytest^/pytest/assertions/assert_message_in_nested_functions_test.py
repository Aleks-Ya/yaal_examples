def test_assert_message_in_nested_function():
    __assert_lists_equal(["a", "b"], ["a", "c"])


def __assert_lists_equal(list1: list[str], list2: list[str]):
    for i, text in enumerate(list1):
        __assert_equal(text, list2[i])


def __assert_equal(text1: str, text2: str):
    assert text1 == text2
