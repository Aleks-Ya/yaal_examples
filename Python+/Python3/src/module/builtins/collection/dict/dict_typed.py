from typing import Dict


def test_typed_dict():
    error_1: RuntimeError = RuntimeError()
    typed_dict: Dict[str, RuntimeError] = {'error': error_1}
    assert typed_dict['error'] == error_1

    error_2: RuntimeError = RuntimeError()
    typed_dict_2: dict[str, RuntimeError] = {'error': error_2}
    assert typed_dict_2['error'] == error_2
