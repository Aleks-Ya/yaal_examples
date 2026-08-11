from langcodes import *


def test_standardize_tag():
    assert standardize_tag('en-Latn') == 'en'
