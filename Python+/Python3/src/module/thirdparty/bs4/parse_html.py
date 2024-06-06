import unittest

from bs4 import BeautifulSoup


class ParseHtmlTestCase(unittest.TestCase):

    def test_parse_html(self):
        exp_fragment = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
        soup = BeautifulSoup(exp_fragment, 'html.parser')
        self.assertEqual("Hello, World!", soup.div.h1.text)
        act_fragment = str(soup)
        self.assertEqual(exp_fragment, act_fragment)


if __name__ == '__main__':
    unittest.main()
