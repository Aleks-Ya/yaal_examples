def test_upper_case():
    s: str = 'abc'
    u: str = s.upper()
    assert u == 'ABC'


def test_lower_case():
    assert 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя'.lower() == 'АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'.lower()


def test_case_fold():
    assert 'abcdefghijklmnopqrstuvwxyz'.casefold() == 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.casefold()
    assert 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя'.casefold() == 'АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'.casefold()
