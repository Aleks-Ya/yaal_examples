import re


def test_split_string_by_pattern():
    text: str = "Cats are smarter\nthan dogs."
    split: list[str] = re.split(r'\s', text)
    assert split == ['Cats', 'are', 'smarter', 'than', 'dogs.']


def test_split_words():
    text: str = "Cats are smarter\nthan dogs."
    split: list[str] = re.split(r'[^a-zA-Z]+', text)
    assert split == ['Cats', 'are', 'smarter', 'than', 'dogs', '']
