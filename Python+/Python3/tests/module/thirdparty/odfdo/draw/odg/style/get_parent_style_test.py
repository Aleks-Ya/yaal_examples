from typing import cast

from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase


def test_get_parent_style(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(
        family='graphic', display_name='My style 1')
    style_base: StyleBase = cast(StyleBase, style)
    parent: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_parent_style(style_base)
    assert parent is not None
    assert parent.get_attribute_string('style:name') == 'standard'
