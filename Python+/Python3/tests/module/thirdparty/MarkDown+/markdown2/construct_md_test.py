import markdown2
from markdown2 import UnicodeWithAttrs


def test_construct_md():
    html: UnicodeWithAttrs = markdown2.markdown("*boo!*")
    assert html == "<p><em>boo!</em></p>\n"
