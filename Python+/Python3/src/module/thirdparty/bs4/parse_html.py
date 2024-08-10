import unittest

from bs4 import BeautifulSoup, Tag


class TestParseHtml(unittest.TestCase):

    def test_parse_html(self):
        exp_fragment: str = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
        soup: BeautifulSoup = BeautifulSoup(exp_fragment, 'html.parser')
        self.assertEqual("Hello, World!", soup.div.h1.text)
        act_fragment: str = str(soup)
        self.assertEqual(exp_fragment, act_fragment)

    def test_find_by_id(self):
        html: str = '<div><h1 id="greeting">Hello, World!</h1></div>'
        soup: BeautifulSoup = BeautifulSoup(html, 'html.parser')
        h1: Tag = soup.find(id="greeting")
        self.assertEqual("Hello, World!", h1.text)


if __name__ == '__main__':
    unittest.main()
