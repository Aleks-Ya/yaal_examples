import textwrap
import unittest

from bs4 import BeautifulSoup, Tag


class TestFormatHtml(unittest.TestCase):

    def setUp(self):
        self.soup: BeautifulSoup = BeautifulSoup()
        div: Tag = self.soup.new_tag('div')
        p: Tag = self.soup.new_tag('p')
        p.string = "This is a sample HTML fragment."
        div.append(p)
        self.soup.append(div)

    def test_prettify(self):
        act: str = str(self.soup.prettify())
        exp: str = textwrap.dedent("""\
                                    <div>
                                     <p>
                                      This is a sample HTML fragment.
                                     </p>
                                    </div>
                                   """)
        self.assertEqual(exp, act)

    def test_minify(self):
        act: str = str(self.soup)
        exp: str = textwrap.dedent("""<div><p>This is a sample HTML fragment.</p></div>""")
        self.assertEqual(exp, act)


if __name__ == '__main__':
    unittest.main()
