import re


def _contains_chinese(text: str) -> bool:
    pattern = r'[\u4e00-\u9fff\u3400-\u4dbf\u2e80-\u2eff\u3000-\u303f\uff00-\uffef]'
    if re.search(pattern, text):
        return True
    return False


assert _contains_chinese('听得见远处传来的步枪声。')
assert not _contains_chinese('')
assert not _contains_chinese(' ')
assert not _contains_chinese('\n')
