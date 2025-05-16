import pykakasi
from pykakasi import kakasi


def test_pykakasi():
    kks: kakasi = pykakasi.kakasi()
    text: str = "かな漢字"
    result: list[dict[str, str]] = kks.convert(text)
    for item in result:
        print("{}: kana '{}', hiragana '{}', romaji: '{}'"
              .format(item['orig'], item['kana'], item['hira'], item['hepburn']))
