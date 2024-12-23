from mdutils import MdUtils


def test_create_list_bullet():
    items: list[str] = ['Item 1', 'Item 2']
    md: MdUtils = MdUtils(file_name="", title="")
    md.new_list(items)
    assert md.get_md_text() == """



- Item 1
- Item 2
"""


def test_create_list_ordered():
    items: list[str] = ['Item 1', 'Item 2']
    md: MdUtils = MdUtils(file_name="", title="")
    md.new_list(items, marked_with="1")
    assert md.get_md_text() == """



1. Item 1
2. Item 2
"""
