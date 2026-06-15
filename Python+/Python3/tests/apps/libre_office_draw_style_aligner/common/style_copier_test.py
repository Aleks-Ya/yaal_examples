import shutil

from odfdo import Document
from odfdo.style_base import PropDict

from common.data_types import FamilyName, StyleDisplayName, OdgPath
from common.doc import Doc
from common.style_copier import StyleCopier
from current_path import get_file_in_current_dir
from temp_helper import TempPath

family: FamilyName = FamilyName('graphic')
display_name_1: StyleDisplayName = StyleDisplayName('My Style 1')
display_name_2: StyleDisplayName = StyleDisplayName('My Style 2')
display_name_3: StyleDisplayName = StyleDisplayName('My Style 3')


def test_copy_style_document():
    src_path: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_src.odg'))
    src_doc: Document = Document(src_path)
    src: Doc = Doc(src_doc)
    properties_1: PropDict = src.get_style_by_display_name(family, display_name_1).get_properties()
    assert src.is_style_exist_by_display_name(family, display_name_1) == True
    assert src.is_style_exist_by_display_name(family, display_name_2) == True
    assert src.is_style_exist_by_display_name(family, display_name_3) == True

    dest_path: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_dest.odg'))
    dest_doc: Document = Document(dest_path)
    dest: Doc = Doc(dest_doc)
    assert dest.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest.is_style_exist_by_display_name(family, display_name_3) == False
    assert properties_1 != dest.get_style_by_display_name(family, display_name_1).get_properties()

    StyleCopier.copy_style_document(src_doc, dest_doc, family, display_name_3)

    assert dest.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest.is_style_exist_by_display_name(family, display_name_2) == True
    assert dest.is_style_exist_by_display_name(family, display_name_3) == True
    assert properties_1 == dest.get_style_by_display_name(family, display_name_1).get_properties()


def test_copy_style_file():
    src_path: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_src.odg'))
    src: Doc = Doc(Document(src_path))
    properties_1: PropDict = src.get_style_by_display_name(family, display_name_1).get_properties()
    assert src.is_style_exist_by_display_name(family, display_name_1) == True
    assert src.is_style_exist_by_display_name(family, display_name_2) == True
    assert src.is_style_exist_by_display_name(family, display_name_3) == True

    dest_path_orig: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_dest.odg'))
    dest_path: OdgPath = OdgPath(TempPath.temp_path_absent(".odg"))
    shutil.copyfile(dest_path_orig, dest_path)

    dest1: Doc = Doc(Document(dest_path))
    assert dest1.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest1.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest1.is_style_exist_by_display_name(family, display_name_3) == False
    assert properties_1 != dest1.get_style_by_display_name(family, display_name_1).get_properties()

    display_names: list[StyleDisplayName] = [display_name_3]
    StyleCopier.copy_style_file(src_path, dest_path, family, display_names)

    dest2: Doc = Doc(Document(dest_path))
    assert dest2.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest2.is_style_exist_by_display_name(family, display_name_2) == True
    assert dest2.is_style_exist_by_display_name(family, display_name_3) == True
    assert properties_1 == dest2.get_style_by_display_name(family, display_name_1).get_properties()


def test_copy_style_files():
    src_path: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_src.odg'))
    src: Doc = Doc(Document(src_path))
    properties_1: PropDict = src.get_style_by_display_name(family, display_name_1).get_properties()
    assert src.is_style_exist_by_display_name(family, display_name_1) == True
    assert src.is_style_exist_by_display_name(family, display_name_2) == True
    assert src.is_style_exist_by_display_name(family, display_name_3) == True

    dest_path_orig: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_dest.odg'))
    dest_path: OdgPath = OdgPath(TempPath.temp_path_absent(".odg"))
    shutil.copyfile(dest_path_orig, dest_path)

    dest1: Doc = Doc(Document(dest_path))
    assert dest1.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest1.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest1.is_style_exist_by_display_name(family, display_name_3) == False
    assert properties_1 != dest1.get_style_by_display_name(family, display_name_1).get_properties()

    display_names: list[StyleDisplayName] = [display_name_3]
    files: dict[OdgPath, OdgPath] = {src_path: dest_path}
    StyleCopier.copy_style_files(files, family, display_names)

    dest2: Doc = Doc(Document(dest_path))
    assert dest2.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest2.is_style_exist_by_display_name(family, display_name_2) == True
    assert dest2.is_style_exist_by_display_name(family, display_name_3) == True
    assert properties_1 == dest2.get_style_by_display_name(family, display_name_1).get_properties()
