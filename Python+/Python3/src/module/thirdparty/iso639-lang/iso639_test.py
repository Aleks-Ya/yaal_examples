from iso639.iso639 import Lang


def test_iso639():
    fr: Lang = Lang("French")
    print(fr)
