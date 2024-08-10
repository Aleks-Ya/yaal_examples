import unittest


class TestCreateString(unittest.TestCase):

    def test_single_quotes(self):
        a = 'a'
        self.assertEqual(a, 'a')

    def test_escape_in_single_quotes(self):
        escape = 'a\'b'
        self.assertEqual(escape, "a'b")

    def test_double_quotes(self):
        b = "ab"
        self.assertEqual(b, 'ab')

    def test_escape_in_double_quotes(self):
        escape = "a\"b"
        self.assertEqual(escape, 'a"b')

    def test_escape_in_raw_strings(self):
        escape = r'a row \n \" string'
        self.assertEqual(escape, 'a row \\n \\" string')

    def test_multi_line_strings(self):
        m = """
a
b
"""
        self.assertEqual(m, '\na\nb\n')

    def test_multi_line_strings_without_indent(self):
        import textwrap
        def get_string():
            return textwrap.dedent("""
                                a
                                b
                                """)

        self.assertEqual(get_string(), '\na\nb\n')

    def test_alternate_multi_line_strings(self):
        m = '''
a
b
'''
        self.assertEqual(m, '\na\nb\n')

    def test_skip_first_newline_in_multi_line_string(self):
        m = """\
a
b
"""
        self.assertEqual(m, 'a\nb\n')

        m = '''\
a
b
'''
        self.assertEqual(m, 'a\nb\n')

    def test_auto_concatenation(self):
        c = 'a' "b"
        self.assertEqual(c, 'ab')

        text = ('Put several strings within parentheses '
                'to have them joined together.')
        self.assertEqual(text, 'Put several strings within parentheses to have them joined together.')


if __name__ == '__main__':
    unittest.main()
