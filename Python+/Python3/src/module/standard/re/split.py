import re
import string
from re import Pattern


def test_split_string_by_pattern():
    text: str = "Cats are smarter\nthan dogs."
    split: list[str] = re.split(r'\s', text)
    assert split == ['Cats', 'are', 'smarter', 'than', 'dogs.']


def test_split_words():
    text: str = "Cats are smarter\nthan dogs."
    split: list[str] = re.split(r'\W', text)
    assert split == ['Cats', 'are', 'smarter', 'than', 'dogs', '']


def test_split_preserved_symbols():
    text: str = "Cats are smarter\nthan dogs."
    split: list[str] = re.split(r'([^a-zA-Z]+)', text)
    assert split == ['Cats', ' ', 'are', ' ', 'smarter', '\n', 'than', ' ', 'dogs', '.', '']


def test_split_by_punctuation_mark():
    text: str = "Cats are, smarter.than dogs!"
    punctuation_pattern: Pattern[str] = re.compile(f"[{re.escape(string.punctuation)}]")
    split: list[str] = re.split(punctuation_pattern, text)
    assert split == ['Cats are', ' smarter', 'than dogs', '']
