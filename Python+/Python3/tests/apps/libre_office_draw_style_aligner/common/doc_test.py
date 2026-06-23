import re
from pathlib import Path

from odfdo import Document
import pytest

from common.data_types import FamilyName, StyleDisplayName, StyleName
from common.doc import Doc
from common.stl import Stl
from current_path import get_file_in_current_dir

family: FamilyName = FamilyName('graphic')
path: Path = get_file_in_current_dir('doc_test.odg')
doc: Doc = Doc(Document(path))


def test_get_styles():
    all_styles: list[Stl] = doc.get_styles(None)
    assert len(all_styles) == 61
    graphic_styles: list[Stl] = doc.get_styles(family)
    assert len(graphic_styles) == 40


def test_get_style():
    style_by_name: Stl = doc.get_style(family, StyleName('My_20_Style_20_1'))
    assert style_by_name.get_name() == 'My_20_Style_20_1'
    assert style_by_name.get_display_name() == 'My Style 1'
    assert style_by_name.get_family() == 'graphic'
    assert style_by_name.is_custom() == True

    style_by_display_name_without_spaces: Stl = doc.get_style(family, StyleDisplayName('My Style 1'))
    assert style_by_display_name_without_spaces.get_name() == 'My_20_Style_20_1'
    assert style_by_display_name_without_spaces.get_display_name() == 'My Style 1'
    assert style_by_display_name_without_spaces.get_family() == 'graphic'
    assert style_by_display_name_without_spaces.is_custom() == True

    style_by_display_name_without_spaces: Stl = doc.get_style(family, StyleDisplayName('MyStyleNoSpaces'))
    assert style_by_display_name_without_spaces.get_name() == 'MyStyleNoSpaces'
    with pytest.raises(ValueError, match="Style display name not found"):
        style_by_display_name_without_spaces.get_display_name()
    assert style_by_display_name_without_spaces.get_family() == 'graphic'
    assert style_by_display_name_without_spaces.is_custom() == True

    with pytest.raises(ValueError, match=re.escape(f"Style not found: 'graphic/NotExists' in '{path}'")):
        doc.get_style(family, StyleDisplayName('NotExists'))


def test_get_style_by_name():
    name: StyleName = StyleName('My_20_Style_20_1')
    style: Stl = doc.get_style_by_name(family, name)
    assert style.get_name() == 'My_20_Style_20_1'
    assert style.get_display_name() == 'My Style 1'
    assert style.get_family() == 'graphic'
    assert style.is_custom() == True


def test_get_style_by_display_name_with_spaces():
    display_name: StyleDisplayName = StyleDisplayName('My Style 1')
    style: Stl = doc.get_style_by_display_name(family, display_name)
    assert style.get_name() == 'My_20_Style_20_1'
    assert style.get_display_name() == 'My Style 1'
    assert style.get_family() == 'graphic'
    assert style.is_custom() == True


# If style name does not contain spaces, its display name is empty
def test_get_style_by_display_name_without_spaces():
    display_name: StyleDisplayName = StyleDisplayName('MyStyleNoSpaces')
    with pytest.raises(ValueError, match="Style not found: graphic/MyStyleNoSpaces"):
        doc.get_style_by_display_name(family, display_name)


def test_is_style_exist_by_name():
    exists: StyleName = StyleName('My_20_Style_20_1')
    not_exists: StyleName = StyleName('NotExists')
    assert doc.is_style_exist_by_name(family, exists) == True
    assert doc.is_style_exist_by_name(family, not_exists) == False


def test_is_style_exist_by_display_name():
    exists: StyleDisplayName = StyleDisplayName('My Style 1')
    not_exists: StyleDisplayName = StyleDisplayName('Not Exists')
    not_exists_without_spaces: StyleDisplayName = StyleDisplayName('MyStyleNoSpaces')
    assert doc.is_style_exist_by_display_name(family, exists) == True
    assert doc.is_style_exist_by_display_name(family, not_exists) == False
    assert doc.is_style_exist_by_display_name(family, not_exists_without_spaces) == False


def test_has_parent_style():
    has_parent: Stl = doc.get_style_by_name(family, StyleName('My_20_Style_20_1'))
    no_parent: Stl = doc.get_style_by_name(family, StyleName('standard'))
    assert doc.has_parent_style(has_parent) == True
    assert doc.has_parent_style(no_parent) == False
