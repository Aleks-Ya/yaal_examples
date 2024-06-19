import unittest

from bs4 import BeautifulSoup, Tag


class TestConstructHtml(unittest.TestCase):

    def setUp(self):
        self.soup: BeautifulSoup = BeautifulSoup()

    def test_construct_html_fragment(self):
        div: Tag = self.soup.new_tag('div')
        h1: Tag = self.soup.new_tag('h1')
        h1.string = "Hello, World!"
        p: Tag = self.soup.new_tag('p')
        p.string = "This is a sample HTML fragment."

        div.append(h1)
        div.append(p)
        self.soup.append(div)

        act_fragment: str = str(self.soup)
        exp_fragment: str = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
        self.assertEqual(exp_fragment, act_fragment)

    def test_construct_tag_with_text_and_nested_tag(self):
        h1: Tag = self.soup.new_tag('h1')
        h1.string = "Variable "
        code: Tag = self.soup.new_tag('code')
        code.string = "username"
        h1.append(code)
        h1.append(" is note defined")
        self.soup.append(h1)

        act_fragment: str = str(self.soup)
        exp_fragment: str = '<h1>Variable <code>username</code> is note defined</h1>'
        self.assertEqual(exp_fragment, act_fragment)


if __name__ == '__main__':
    unittest.main()
