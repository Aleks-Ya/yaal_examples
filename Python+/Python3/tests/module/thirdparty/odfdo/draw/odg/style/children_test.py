from odfdo import Document, DrawFillImage, DrawMarker, Element
from odfdo.style_base import StyleBase


def test_children(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(
        family='graphic', name_or_element='My_20_style_20_1')
    assert isinstance(style, StyleBase)
    children: list[Element] = style.children
    assert len(children) == 2
