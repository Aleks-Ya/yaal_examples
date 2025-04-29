from bs4 import BeautifulSoup, Tag


def test_compare_str_and_str():
    exp_fragment: str = '<div><h1>Hello, World!</h1><p>This is a sample HTML fragment.</p></div>'
    soup: BeautifulSoup = BeautifulSoup(exp_fragment, 'html.parser')
    assert "Hello, World!" == soup.div.h1.text
    act_fragment: str = str(soup)
    assert exp_fragment == act_fragment


def test_compare_soup_and_soup():
    soup1: BeautifulSoup = create_soup()
    soup2: BeautifulSoup = create_soup()
    assert soup1 == soup2
    assert str(soup1) == str(soup2)


def test_compare_soup_and_str():
    soup1: BeautifulSoup = create_soup()
    html: str = "<div><p>This is a sample HTML fragment.</p></div>"
    soup2: BeautifulSoup = BeautifulSoup(html, 'html.parser')
    assert soup1 == soup2
    assert str(soup1) == str(soup2)


def test_compare_prettified_str_and_minimized_str():
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


def create_soup() -> BeautifulSoup:
    soup: BeautifulSoup = BeautifulSoup()
    div: Tag = soup.new_tag('div')
    p: Tag = soup.new_tag('p')
    p.string = "This is a sample HTML fragment."
    div.append(p)
    soup.append(div)
    return soup
