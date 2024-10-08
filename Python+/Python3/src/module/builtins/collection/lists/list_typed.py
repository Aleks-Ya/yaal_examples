from typing import List


def test_typed_list_append():
    typed_list: List[RuntimeError] = [RuntimeError()]

    new_element: RuntimeError = RuntimeError("#2")
    typed_list.append(new_element)
    assert new_element in typed_list  # Assertion for 'appending' test


def test_typed_list_iteration():
    str_list: List[str] = ['a', 'b']
    for expected_s in str_list:
        assert expected_s in str_list  # Assertion for each 'iteration' test
