def test_single_quotes():
    a: str = 'a'
    assert a == 'a'


def test_escape_in_single_quotes():
    escape: str = 'a\'b'
    assert escape == "a'b"


def test_double_quotes():
    b: str = "ab"
    assert b == 'ab'


def test_escape_in_double_quotes():
    escape: str = "a\"b"
    assert escape == 'a"b'


def test_escape_in_raw_strings():
    escape: str = r'a row \n \" string'
    assert escape == 'a row \\n \\" string'


def test_multi_line_strings():
    m: str = """
a
b
"""
    assert m == '\na\nb\n'


def test_multi_line_strings_without_indent():
    import textwrap
    def get_string():
        return textwrap.dedent("""
                            a
                            b
                            """)

    assert get_string() == '\na\nb\n'


def test_alternate_multi_line_strings():
    m: str = '''
a
b
'''
    assert m == '\na\nb\n'


def test_skip_first_newline_in_multi_line_string():
    m: str = """\
a
b
"""
    assert m == 'a\nb\n'

    m = '''\
a
b
'''
    assert m == 'a\nb\n'


def test_auto_concatenation():
    c: str = 'a' "b"
    assert c == 'ab'

    text: str = ('Put several strings within parentheses '
                 'to have them joined together.')
    assert text == 'Put several strings within parentheses to have them joined together.'
