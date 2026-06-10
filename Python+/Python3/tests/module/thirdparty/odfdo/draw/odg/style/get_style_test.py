from typing import cast

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


def test_change_style_display_name(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(family='graphic',
                                                                              display_name='My style 1')
    style_base: StyleBase = cast(StyleBase, style)
    assert style_base.get_attribute_string('style:family') == 'graphic'
    assert style_base.get_attribute_string('style:name') == 'My_20_style_20_1'
    assert style_base.get_attribute_string('style:display-name') == 'My style 1'

    new_display_name: str = 'My style 1 Modified'
    style_base.set_style_attribute('style:display-name', new_display_name)
    assert style_base.get_attribute_string('style:family') == 'graphic'
    assert style_base.get_attribute_string('style:name') == 'My_20_style_20_1'
    assert style_base.get_attribute_string('style:display-name') == new_display_name

    updated_style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(family='graphic',
                                                                                      display_name=new_display_name)
    updated_style_base: StyleBase = cast(StyleBase, updated_style)
    assert updated_style_base.get_attribute_string('style:family') == 'graphic'
    assert updated_style_base.get_attribute_string('style:name') == 'My_20_style_20_1'
    assert updated_style_base.get_attribute_string('style:display-name') == new_display_name


def test_change_style_name(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(family='graphic',
                                                                              display_name='My style 1')
    assert style is not None
    assert style.get_attribute_string('style:family') == 'graphic'
    assert style.get_attribute_string('style:name') == 'My_20_style_20_1'
    assert style.get_attribute_string('style:display-name') == 'My style 1'

    new_name: str = 'My_20_style_20_1_Modfied'
    style.set_style_attribute('style:name', new_name)
    assert style.get_attribute_string('style:family') == 'graphic'
    assert style.get_attribute_string('style:name') == new_name
    assert style.get_attribute_string('style:display-name') == 'My style 1'

    updated_style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(family='graphic',
                                                                                      display_name='My style 1')
    assert updated_style is not None
    assert updated_style.get_attribute_string('style:family') == 'graphic'
    assert updated_style.get_attribute_string('style:name') == new_name
    assert updated_style.get_attribute_string('style:display-name') == 'My style 1'
