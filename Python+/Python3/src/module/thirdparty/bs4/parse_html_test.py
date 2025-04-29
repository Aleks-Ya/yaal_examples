from bs4 import BeautifulSoup, Tag


def test_parse_html():
    exp_fragment: str = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
    soup: BeautifulSoup = BeautifulSoup(exp_fragment, 'html.parser')
    assert "Hello, World!" == soup.div.h1.text
    act_fragment: str = str(soup)
    assert exp_fragment == act_fragment


def test_find_by_id():
    html: str = '<div><h1 id="greeting">Hello, World!</h1></div>'
    soup: BeautifulSoup = BeautifulSoup(html, 'html.parser')
    h1: Tag = soup.find(id="greeting")
    assert "Hello, World!" == h1.text
