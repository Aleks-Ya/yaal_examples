import re


def __contains_chinese(text: str) -> bool:
    pattern: str = r'[\u4e00-\u9fff\u3400-\u4dbf\u2e80-\u2eff\u3000-\u303f\uff00-\uffef]'
    contains: bool = re.search(pattern, text) is not None
    return contains


def test_contains_chinese():
    assert __contains_chinese('听得见远处传来的步枪声。')
    assert not __contains_chinese('')
    assert not __contains_chinese(' ')
    assert not __contains_chinese('\n')
