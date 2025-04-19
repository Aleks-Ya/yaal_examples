from typing import Optional, Any

import pycountry
from pycountry import Languages


def test_language():
    languages: Languages = pycountry.languages
    language: Optional[Any] = languages.get(alpha_2='en')
    language_name: Optional[str] = language.name if language else None
    assert language_name == 'English'
