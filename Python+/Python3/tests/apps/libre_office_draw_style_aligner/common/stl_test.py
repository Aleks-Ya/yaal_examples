from odfdo import Document

from common.data_types import FamilyName, StyleName
from common.doc import Doc
from common.stl import Stl
from current_path import get_file_in_current_dir

family: FamilyName = FamilyName('graphic')
doc: Doc = Doc(Document(get_file_in_current_dir('stl_test.odg')))


def test_is_custom():
    styles: list[Stl] = doc.get_styles(None)
    assert len(styles) == 61
    custom_styles: list[Stl] = [style for style in styles if style.is_custom()]
    assert len(custom_styles) == 59


def test_style_custom():
    style: Stl = doc.get_style(family, StyleName('My_20_Style_20_1'))
    assert style.has_family()
    assert style.has_name()
    assert style.has_display_name()
    assert style.get_family() == 'graphic'
    assert style.get_name() == 'My_20_Style_20_1'
    assert style.get_display_name() == 'My Style 1'
    assert style.is_custom() == True
    assert str(style) == 'Stl(graphic-My_20_Style_20_1-My Style 1)'


def test_style_custom_no_spaces():
    style: Stl = doc.get_style(family, StyleName('MyStyleNoSpaces'))
    assert style.has_family()
    assert style.has_name()
    assert not style.has_display_name()
    assert style.get_family() == 'graphic'
    assert style.get_name() == 'MyStyleNoSpaces'
    assert style.is_custom() == True
    assert str(style) == 'Stl(graphic-MyStyleNoSpaces-<>)'


def test_style_standard():
    style: Stl = doc.get_style(family, StyleName('standard'))
    assert style.has_family()
    assert style.has_name()
    assert not style.has_display_name()
    assert style.get_family() == 'graphic'
    assert style.get_name() == 'standard'
    assert style.is_custom() == False
    assert str(style) == 'Stl(graphic-standard-<>)'


def test_style_object_without_fill():
    style: Stl = doc.get_style(family, StyleName('objectwithoutfill'))
    assert style.has_family()
    assert style.has_name()
    assert not style.has_display_name()
    assert style.get_family() == 'graphic'
    assert style.get_name() == 'objectwithoutfill'
    assert style.is_custom() == True
    assert str(style) == 'Stl(graphic-objectwithoutfill-<>)'
