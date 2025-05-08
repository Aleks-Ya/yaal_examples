from mdutils import MdUtils


def test_create_table():
    text: list[str] = ["Header 1", "Header 2", "C", "D", "E", "F"]
    md: MdUtils = MdUtils(file_name="", title="")
    md.new_table(2, 3, text)
    assert md.get_md_text() == """



|Header 1|Header 2|
| :---: | :---: |
|C|D|
|E|F|
"""
