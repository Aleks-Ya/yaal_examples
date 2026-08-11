from typing import Iterator

from janome.tokenizer import Tokenizer, Token


def test_janome():
    t: Tokenizer = Tokenizer()
    tokenized: Iterator[Token] = t.tokenize('すもももももももものうち')
    for token in tokenized:
        print(token)
