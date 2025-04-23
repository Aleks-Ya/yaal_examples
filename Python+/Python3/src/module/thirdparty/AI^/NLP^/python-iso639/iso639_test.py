from iso639.language import Language


def test_iso639():
    lang1: Language = Language.from_part3('fra')
    print(lang1)
