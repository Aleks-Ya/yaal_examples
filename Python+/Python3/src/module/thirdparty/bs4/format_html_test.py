import textwrap

import pytest
from bs4 import BeautifulSoup, Tag


@pytest.fixture
def soup():
    soup: BeautifulSoup = BeautifulSoup()
    div: Tag = soup.new_tag('div')
    p: Tag = soup.new_tag('p')
    p.string = "This is a sample HTML fragment."
    div.append(p)
    soup.append(div)
    return soup


def test_prettify(soup):
    act: str = str(soup.prettify())
    exp: str = textwrap.dedent("""\
                                <div>
                                 <p>
                                  This is a sample HTML fragment.
                                 </p>
                                </div>
                               """)
    assert exp == act


def test_minify(soup):
    act: str = str(soup)

    exp: str = textwrap.dedent("""<div><p>This is a sample HTML fragment.</p></div>""")
    assert exp == act
