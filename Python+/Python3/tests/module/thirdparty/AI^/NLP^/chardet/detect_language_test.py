import chardet


def test_detect_language():
    result: dict = chardet.detect(b'Your text here in bytes')
    assert result == {'encoding': 'ascii', 'confidence': 1.0, 'language': ''}
