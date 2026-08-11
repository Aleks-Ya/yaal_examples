from mdutils import MdUtils


def test_create_table():
    md: MdUtils = MdUtils(file_name="", title="")
    text: list[str] = ["Header 1", "Header 2", "C", "D", "E", "F"]
    md.new_table(2, 3, text)
    assert md.get_md_text() == """



|Header 1|Header 2|
| :---: | :---: |
|C|D|
|E|F|
"""
