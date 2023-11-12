# Create strings
import textwrap

# single quotes
a = 'a'

# escape in single quotes
escape = 'a\'b'
assert escape == "a'b"

# double quotes
b = "ab"

# escape in double quotes
escape = "a\"b"
assert escape == 'a"b'

# escape in raw strings
escape = r'a row \n \" string'
assert escape == 'a row \\n \\" string'

# multi line strings
m = """
a
b
"""
assert m == '\na\nb\n'


# multi line strings without indent
def get_string():
    return textwrap.dedent("""
                            a
                            b
                            """)


assert get_string() == '\na\nb\n'

# ?
m = '''
a
b
'''
assert m == '\na\nb\n'

# skip the 1st \n in a multi line string
m = """\
a
b
"""
assert m == 'a\nb\n'

m = '''\
a
b
'''
assert m == 'a\nb\n'

# auto concatenation
c = 'a' "b"
assert c == 'ab'

text = ('Put several strings within parentheses '
        'to have them joined together.')
assert text == 'Put several strings within parentheses to have them joined together.'
