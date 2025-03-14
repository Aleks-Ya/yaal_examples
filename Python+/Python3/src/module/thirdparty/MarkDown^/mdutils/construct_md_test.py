import tempfile
from pathlib import Path

from mdutils import MdUtils


def test_construct_md():
    file: str = tempfile.mkstemp(suffix=".md")[1]
    print(file)
    mdFile: MdUtils = MdUtils(file_name=file, title='Markdown File Example')
    mdFile.new_header(1, "Introduction")
    mdFile.new_line("Welcome")
    mdFile.create_md_file()
    assert Path(file).read_text() == """
Markdown File Example
=====================

# Introduction
  
Welcome"""


def test_construct_str():
    mdFile: MdUtils = MdUtils(file_name="", title='Markdown File Example')
    mdFile.new_header(1, "Introduction")
    mdFile.new_line("Welcome")
    text: str = mdFile.get_md_text()
    assert text == """
Markdown File Example
=====================

# Introduction
  
Welcome"""
