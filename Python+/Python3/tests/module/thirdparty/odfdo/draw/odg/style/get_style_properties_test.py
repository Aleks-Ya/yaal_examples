from pathlib import Path

import pytest
from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase

from temp_helper import TempPath


def test_get_style_properties(draw_doc: Document):
    properties: dict[str, str] | None = draw_doc.get_style_properties(family='graphic', name='My_20_style_20_1')
    assert properties == {'draw:stroke-dash': 'Dash_20_Dot_20_4',
                          'draw:fill': 'gradient',
                          'draw:fill-color': '#729fcf',
                          'draw:fill-gradient-name': 'Pastel_20_Bouquet',
                          'draw:gradient-step-count': '0'}


@pytest.mark.skip(reason="fix it")
def test_get_paragraph_style_properties(draw_doc: Document):
    properties: dict[str, str] | None = draw_doc.get_style_properties(family='paragraph', name='My_20_style_20_1')
    assert properties == {'draw:stroke-dash': 'Dash_20_Dot_20_4',
                          'draw:fill': 'gradient',
                          'draw:fill-color': '#729fcf',
                          'draw:fill-gradient-name': 'Pastel_20_Bouquet',
                          'draw:gradient-step-count': '0'}


@pytest.mark.skip(reason="fix it")
def test_make_style_bold(draw_doc: Document):
    style: StyleBase | DrawFillImage | DrawMarker | None = draw_doc.get_style(family='graphic',
                                                                              name_or_element='My_20_style_20_1')
    assert style is not None
    style.set_style_attribute('fo:font-weight', 'bold')
    out: Path = TempPath.temp_path_absent(".odg")
    draw_doc.save(out)
    properties: dict[str, str] | None = draw_doc.get_style_properties(family='graphic', name='My_20_style_20_1')
    assert properties.get('fo:font-weight') == 'bold'
