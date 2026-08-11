def assert_lists_equal(list1: list[str], list2: list[str]):
    for i, text in enumerate(list1):
        assert_equal(text, list2[i])


def assert_equal(text1: str, text2: str):
    assert text1 == text2
