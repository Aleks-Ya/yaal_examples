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
display_name_4: StyleDisplayName = StyleDisplayName('My Style 4')
src_path: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_src.odg'))
dest_path_orig: OdgPath = OdgPath(get_file_in_current_dir('style_copier_test_dest.odg'))


def test_copy_style_document():
    src_document: Document = Document(src_path)
    src_doc: Doc = Doc(src_document)
    properties_1: PropDict = src_doc.get_style_by_display_name(family, display_name_1).get_properties()
    assert src_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_4) == True

    dest_document: Document = Document(dest_path_orig)
    dest_doc: Doc = Doc(dest_document)
    assert dest_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest_doc.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest_doc.is_style_exist_by_display_name(family, display_name_3) == False
    assert dest_doc.is_style_exist_by_display_name(family, display_name_4) == False
    assert dest_doc.get_style_by_display_name(family, display_name_1).get_properties() != properties_1

    copied_styles: list[StyleDisplayName] = StyleCopier.copy_style_document(
        src_document, dest_document, family, display_name_3)
    assert copied_styles == [display_name_1, display_name_2, display_name_3]
    assert src_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_4) == True

    assert dest_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert dest_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert dest_doc.is_style_exist_by_display_name(family, display_name_4) == False
    assert dest_doc.get_style_by_display_name(family, display_name_1).get_properties() == properties_1


def test_copy_style_file():
    src_doc: Doc = Doc(Document(src_path))
    properties_1: PropDict = src_doc.get_style_by_display_name(family, display_name_1).get_properties()
    assert src_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_4) == True

    dest_path: OdgPath = OdgPath(TempPath.temp_path_absent(".odg"))
    shutil.copyfile(dest_path_orig, dest_path)

    dest_doc_1: Doc = Doc(Document(dest_path))
    assert dest_doc_1.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest_doc_1.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest_doc_1.is_style_exist_by_display_name(family, display_name_3) == False
    assert dest_doc_1.is_style_exist_by_display_name(family, display_name_4) == False
    assert dest_doc_1.get_style_by_display_name(family, display_name_1).get_properties() != properties_1

    display_names: list[StyleDisplayName] = [display_name_3]
    copied_styles: list[StyleDisplayName] = StyleCopier.copy_style_file(src_path, dest_path, family, display_names)
    assert copied_styles == [display_name_1, display_name_2, display_name_3]
    assert src_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_4) == True

    dest_doc_2: Doc = Doc(Document(dest_path))
    assert dest_doc_2.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest_doc_2.is_style_exist_by_display_name(family, display_name_2) == True
    assert dest_doc_2.is_style_exist_by_display_name(family, display_name_3) == True
    assert dest_doc_2.is_style_exist_by_display_name(family, display_name_4) == False
    assert dest_doc_2.get_style_by_display_name(family, display_name_1).get_properties() == properties_1


def test_copy_style_files():
    src_doc: Doc = Doc(Document(src_path))
    properties_1: PropDict = src_doc.get_style_by_display_name(family, display_name_1).get_properties()
    assert src_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_4) == True

    dest_path_a: OdgPath = OdgPath(TempPath.temp_path_absent(".odg"))
    shutil.copyfile(dest_path_orig, dest_path_a)

    dest_path_b: OdgPath = OdgPath(TempPath.temp_path_absent(".odg"))
    shutil.copyfile(dest_path_orig, dest_path_b)

    dest_doc_a1: Doc = Doc(Document(dest_path_a))
    assert dest_doc_a1.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest_doc_a1.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest_doc_a1.is_style_exist_by_display_name(family, display_name_3) == False
    assert dest_doc_a1.is_style_exist_by_display_name(family, display_name_4) == False
    assert dest_doc_a1.get_style_by_display_name(family, display_name_1).get_properties() != properties_1

    dest_doc_b1: Doc = Doc(Document(dest_path_b))
    assert dest_doc_b1.is_style_exist_by_display_name(family, display_name_1) == True
    assert dest_doc_b1.is_style_exist_by_display_name(family, display_name_2) == False
    assert dest_doc_b1.is_style_exist_by_display_name(family, display_name_3) == False
    assert dest_doc_b1.is_style_exist_by_display_name(family, display_name_4) == False
    assert dest_doc_b1.get_style_by_display_name(family, display_name_1).get_properties() != properties_1

    display_names: list[StyleDisplayName] = [display_name_3, display_name_4]
    dest_paths: list[OdgPath] = [dest_path_a, dest_path_b]
    copied_styles: list[StyleDisplayName] = StyleCopier.copy_style_files(src_path, dest_paths, family, display_names)
    assert copied_styles == [display_name_1, display_name_2, display_name_3, display_name_4]
    assert src_doc.is_style_exist_by_display_name(family, display_name_1) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_2) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_3) == True
    assert src_doc.is_style_exist_by_display_name(family, display_name_4) == True

    act_dest_doc_a: Doc = Doc(Document(dest_path_a))
    assert act_dest_doc_a.is_style_exist_by_display_name(family, display_name_1) == True
    assert act_dest_doc_a.is_style_exist_by_display_name(family, display_name_2) == True
    assert act_dest_doc_a.is_style_exist_by_display_name(family, display_name_3) == True
    assert act_dest_doc_a.is_style_exist_by_display_name(family, display_name_4) == True
    assert act_dest_doc_a.get_style_by_display_name(family, display_name_1).get_properties() == properties_1

    act_dest_doc_b: Doc = Doc(Document(dest_path_b))
    assert act_dest_doc_b.is_style_exist_by_display_name(family, display_name_1) == True
    assert act_dest_doc_b.is_style_exist_by_display_name(family, display_name_2) == True
    assert act_dest_doc_b.is_style_exist_by_display_name(family, display_name_3) == True
    assert act_dest_doc_b.is_style_exist_by_display_name(family, display_name_4) == True
    assert act_dest_doc_b.get_style_by_display_name(family, display_name_1).get_properties() == properties_1
