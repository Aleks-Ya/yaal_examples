import unittest

from bs4 import BeautifulSoup, Tag


class TestCompareHtml(unittest.TestCase):

    def test_compare_str_and_str(self):
        exp_fragment: str = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
        soup: BeautifulSoup = BeautifulSoup(exp_fragment, 'html.parser')
        self.assertEqual("Hello, World!", soup.div.h1.text)
        act_fragment: str = str(soup)
        self.assertEqual(exp_fragment, act_fragment)

    def test_compare_soup_and_soup(self):
        soup1: BeautifulSoup = self.__create_soup()
        soup2: BeautifulSoup = self.__create_soup()
        assert soup1 == soup2
        assert str(soup1) == str(soup2)

    def test_compare_soup_and_str(self):
        soup1: BeautifulSoup = self.__create_soup()
        html: str = "<div><p>This is a sample HTML fragment.</p></div>"
        soup2: BeautifulSoup = BeautifulSoup(html, 'html.parser')
        assert soup1 == soup2
        assert str(soup1) == str(soup2)

    def test_compare_prettified_str_and_minimized_str(self):
        html1: str = """
                    <div>
                    <p>
                    This is a sample HTML fragment.
                    </p>
                    </div>
                    """
        soup1: BeautifulSoup = BeautifulSoup(html1, 'html.parser')
        html2: str = "<div><p>This is a sample HTML fragment.</p></div>"
        soup2: BeautifulSoup = BeautifulSoup(html2, 'html.parser')
        assert soup1.prettify() == soup2.prettify()
        assert str(soup1.prettify()) == str(soup2.prettify())

    @staticmethod
    def __create_soup() -> BeautifulSoup:
        soup: BeautifulSoup = BeautifulSoup()
        div: Tag = soup.new_tag('div')
        p: Tag = soup.new_tag('p')
        p.string = "This is a sample HTML fragment."
        div.append(p)
        soup.append(div)
        return soup


if __name__ == '__main__':
    unittest.main()