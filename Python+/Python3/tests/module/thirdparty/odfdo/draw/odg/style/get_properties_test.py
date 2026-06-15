from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase, PropDict


def test_get_properties(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(
        family='graphic', name_or_element='My_20_style_20_1')
    assert style is not None
    if not isinstance(style, StyleBase):
        raise Exception("Style is not a DrawFillImage or DrawMarker")
    style_base: StyleBase = style
    properties: PropDict | None = style_base.get_properties()
    assert properties is not None
    assert properties == {'draw:fill': 'gradient',
                          'draw:fill-color': '#729fcf',
                          'draw:fill-gradient-name': 'Pastel_20_Bouquet',
                          'draw:gradient-step-count': '0',
                          'draw:stroke-dash': 'Dash_20_Dot_20_4'}
