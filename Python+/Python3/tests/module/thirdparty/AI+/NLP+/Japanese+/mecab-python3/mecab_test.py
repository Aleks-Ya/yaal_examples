import MeCab
from MeCab import Tagger


def test_parse():
    wakati: Tagger = MeCab.Tagger("-Owakati")
    parsed: str = wakati.parse("pythonが大好きです")
    split = parsed.split()
    assert split == ['python', 'が', '大好き', 'です']


def test_tagger():
    tagger: Tagger = MeCab.Tagger()
    parsed: str = tagger.parse("pythonが大好きです")
    print(parsed)
