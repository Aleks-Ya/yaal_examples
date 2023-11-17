import re
from typing import List


def highlight(collocation: str, text: str):
    collocation = _remove_prefix(collocation)
    words: List[str] = collocation.split(" ")
    words_regexp: List[str] = [fr"{word[:len(word) - 1]}\w*" if len(word) > 2 else word for word in words]
    base_collocation: str = ' '.join(words_regexp)
    return re.sub(fr'((?<!<b>){base_collocation}(?<!</b>))', r'<b>\1</b>', text, flags=re.IGNORECASE)


def _remove_prefix(word):
    prefixes: List[str] = ["to ", "a ", "an "]
    for prefix in prefixes:
        word = word[len(prefix):] if word.startswith(prefix) else word
    return word
