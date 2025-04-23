import locale


def test_change_current_local():
    locale.setlocale(locale.LC_ALL, '')
    f: str = "{:n}".format(1234567890)
    assert f == '1,234,567,890'
