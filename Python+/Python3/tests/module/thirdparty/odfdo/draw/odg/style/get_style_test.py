from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase


def test_get_style_by_name(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(
        family='graphic', name_or_element='My_20_style_20_1')
    assert style is not None
    assert style.get_attribute_string('style:family') == 'graphic'
    assert style.get_attribute_string('style:name') == 'My_20_style_20_1'
    assert style.get_attribute_string('style:display-name') == 'My style 1'
    assert style.get_attribute_string('style:parent-style-name') == 'standard'
    assert style.tag == 'style:style'


def test_get_style_by_display_name(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(
        family='graphic', display_name='My style 1')
    assert style is not None
    assert style.get_attribute_string('style:family') == 'graphic'
    assert style.get_attribute_string('style:name') == 'My_20_style_20_1'
    assert style.get_attribute_string('style:display-name') == 'My style 1'
    assert style.get_attribute_string('style:parent-style-name') == 'standard'
    assert style.tag == 'style:style'


def test_get_builtin_style(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(
        family='graphic', name_or_element='Text')
    assert style is not None
    assert style.get_attribute_string('style:family') == 'graphic'
    assert style.get_attribute_string('style:name') == 'Text'
    assert style.get_attribute_string('style:display-name') is None
    assert style.get_attribute_string('style:parent-style-name') is None
    assert style.tag == 'style:style'
