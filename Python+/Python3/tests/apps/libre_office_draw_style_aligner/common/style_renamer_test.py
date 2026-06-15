from pathlib import Path

from odfdo import Document, DrawFillImage, DrawMarker
from odfdo.style_base import StyleBase

from common.data_types import FamilyName, StyleName, StyleDisplayName
from common.style_renamer import StyleRenamer
from current_path import get_file_in_current_dir
from temp_helper import TempPath


def test_rename_style():
    in_file: Path = get_file_in_current_dir('style_renamer_test.odg')
    doc: Document = Document(in_file)

    family: FamilyName = FamilyName('graphic')
    old_style_name: StyleName = StyleName('My_20_Style_20_Old')
    old_style_display_name: StyleDisplayName = StyleDisplayName('My Style Old')

    style: StyleBase | DrawFillImage | DrawMarker | None = doc.get_style(
        family=family, display_name=old_style_display_name)
    assert style is not None
    assert style.get_attribute_string('style:family') == family
    assert style.get_attribute_string('style:name') == old_style_name
    assert style.get_attribute_string('style:display-name') == old_style_display_name

    new_style_name: StyleName = StyleName('MyStyleNew')
    new_style_display_name: StyleDisplayName = StyleDisplayName('My Style New')
    StyleRenamer.rename_style(doc=doc, family=family, old_style_display_name=old_style_display_name,
                              new_style_name=new_style_name, new_style_display_name=new_style_display_name)

    out_file: Path = TempPath.temp_path_absent(".odg")
    doc.save(out_file)

    act_doc: Document = Document(out_file)
    act_style: StyleBase | DrawFillImage | DrawMarker | None = act_doc.get_style(
        family=family, display_name=new_style_display_name)
    assert act_style is not None
    assert act_style.get_attribute_string('style:family') == family
    assert act_style.get_attribute_string('style:name') == new_style_name
    assert act_style.get_attribute_string('style:display-name') == new_style_display_name


def test_rename_style_display_name():
    in_file: Path = get_file_in_current_dir('style_renamer_test.odg')
    doc: Document = Document(in_file)

    family: FamilyName = FamilyName('graphic')
    style_name: StyleName = StyleName('My_20_Style_20_Old')
    old_style_display_name: StyleDisplayName = StyleDisplayName('My Style Old')

    style: StyleBase | DrawFillImage | DrawMarker | None = doc.get_style(
        family=family, display_name=old_style_display_name)
    assert style is not None
    assert style.get_attribute_string('style:family') == family
    assert style.get_attribute_string('style:name') == style_name
    assert style.get_attribute_string('style:display-name') == old_style_display_name

    new_style_display_name: StyleDisplayName = StyleDisplayName('My Style New')
    StyleRenamer.rename_style_display_name(doc=doc, family=family, old_style_display_name=old_style_display_name,
                                           new_style_display_name=new_style_display_name)

    out_file: Path = TempPath.temp_path_absent(".odg")
    doc.save(out_file)

    act_doc: Document = Document(out_file)
    act_style: StyleBase | DrawFillImage | DrawMarker | None = act_doc.get_style(
        family=family, display_name=new_style_display_name)
    assert act_style is not None
    assert act_style.get_attribute_string('style:family') == family
    assert act_style.get_attribute_string('style:name') == style_name
    assert act_style.get_attribute_string('style:display-name') == new_style_display_name


def test_rename_style_name():
    in_file: Path = get_file_in_current_dir('style_renamer_test.odg')
    doc: Document = Document(in_file)

    family: FamilyName = FamilyName('graphic')
    old_style_name: StyleName = StyleName('My_20_Style_20_Old')
    style_display_name: StyleDisplayName = StyleDisplayName('My Style Old')

    style: StyleBase | DrawFillImage | DrawMarker | None = doc.get_style(
        family=family, display_name=style_display_name)
    assert style is not None
    assert style.get_attribute_string('style:family') == family
    assert style.get_attribute_string('style:name') == old_style_name
    assert style.get_attribute_string('style:display-name') == style_display_name

    new_style_name: StyleName = StyleName('MyStyleNew')
    StyleRenamer.rename_style_name(doc=doc, family=family, old_name=old_style_name, new_name=new_style_name)

    out_file: Path = TempPath.temp_path_absent(".odg")
    doc.save(out_file)

    act_doc: Document = Document(out_file)
    act_style: StyleBase | DrawFillImage | DrawMarker | None = act_doc.get_style(
        family=family, display_name=style_display_name)
    assert act_style is not None
    assert act_style.get_attribute_string('style:family') == family
    assert act_style.get_attribute_string('style:name') == new_style_name
    assert act_style.get_attribute_string('style:display-name') == style_display_name
