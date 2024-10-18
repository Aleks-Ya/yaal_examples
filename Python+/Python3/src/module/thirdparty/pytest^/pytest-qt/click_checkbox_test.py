from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QCheckBox
from pytestqt.qtbot import QtBot

__clicked: bool = False


def __on_state_changed(_: int):
    global __clicked
    __clicked = True


def test_check_checkbox(qtbot: QtBot):
    checkbox: QCheckBox = QCheckBox()
    # noinspection PyUnresolvedReferences
    checkbox.stateChanged.connect(__on_state_changed)
    checkbox.show()
    qtbot.addWidget(checkbox)
    assert not checkbox.isChecked()
    assert not __clicked
    qtbot.mouseClick(checkbox, Qt.MouseButton.LeftButton)
    assert checkbox.isChecked()
    assert __clicked
