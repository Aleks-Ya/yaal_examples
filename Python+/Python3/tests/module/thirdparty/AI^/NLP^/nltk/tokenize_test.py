import nltk
import pytest


@pytest.mark.skip(reason="Run once")
def test_download_data():
    nltk.download()


def test_tokenize():
    sentence: str = """At eight o'clock on Thursday morning Arthur didn't feel very good."""
    tokens: list[str] = nltk.word_tokenize(sentence)
    assert tokens == ['At', 'eight', "o'clock", 'on', 'Thursday', 'morning',
                      'Arthur', 'did', "n't", 'feel', 'very', 'good', '.']
    tagged: object = nltk.pos_tag(tokens)
    assert tagged == [('At', 'IN'),
                      ('eight', 'CD'),
                      ("o'clock", 'NN'),
                      ('on', 'IN'),
                      ('Thursday', 'NNP'),
                      ('morning', 'NN'),
                      ('Arthur', 'NNP'),
                      ('did', 'VBD'),
                      ("n't", 'RB'),
                      ('feel', 'VB'),
                      ('very', 'RB'),
                      ('good', 'JJ'),
                      ('.', '.')]
