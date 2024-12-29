from typing import Optional

import pycountry
from pycountry import Languages


def test_language():
    languages: Languages = pycountry.languages
    language: Optional[any] = languages.get(alpha_2='en')
    language_name: Optional[str] = language.name if language else None
    assert language_name == 'English'
