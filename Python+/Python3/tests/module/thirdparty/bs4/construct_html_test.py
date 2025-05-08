import pytest
from bs4 import BeautifulSoup, Tag


@pytest.fixture
def soup():
    return BeautifulSoup()


def test_construct_html_fragment(soup):
    div: Tag = soup.new_tag('div')
    h1: Tag = soup.new_tag('h1')
    h1.string = "Hello, World!"
    p: Tag = soup.new_tag('p')
    p.string = "This is a sample HTML fragment."

    div.append(h1)
    div.append(p)
    soup.append(div)

    act_fragment: str = str(soup)
    exp_fragment: str = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
    assert exp_fragment == act_fragment


def test_construct_tag_with_text_and_nested_tag(soup):
    h1: Tag = soup.new_tag('h1')
    h1.string = "Variable "
    code: Tag = soup.new_tag('code')
    code.string = "username"
    h1.append(code)
    h1.append(" is note defined")
    soup.append(h1)

    act_fragment: str = str(soup)
    exp_fragment: str = '<h1>Variable <code>username</code> is note defined</h1>'
    assert exp_fragment == act_fragment


def test_add_title(soup):
    div: Tag = soup.new_tag('div', attrs={"title": "Tooltip hint"})
    div.string = "Text"
    soup.append(div)

    act_fragment: str = str(soup)
    exp_fragment: str = '<div title="Tooltip hint">Text</div>'
    assert exp_fragment == act_fragment
