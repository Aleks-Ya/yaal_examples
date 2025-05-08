from PyQt6.QtWidgets import QLineEdit
from pytestqt.qtbot import QtBot


def test_key_clicks(qtbot: QtBot):
    line_edit: QLineEdit = QLineEdit()
    assert line_edit.text() == ""
    exp_text: str = "Hello"
    qtbot.keyClicks(line_edit, exp_text)
    assert line_edit.text() == exp_text
