from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QCheckBox
from pytestqt.qtbot import QtBot

clicked: bool = False


def on_state_changed(_: int):
    global clicked
    clicked = True


def test_check_checkbox(qtbot: QtBot):
    checkbox: QCheckBox = QCheckBox()
    checkbox.stateChanged.connect(on_state_changed)
    checkbox.show()
    qtbot.addWidget(checkbox)
    assert not checkbox.isChecked()
    assert not clicked
    qtbot.mouseClick(checkbox, Qt.MouseButton.LeftButton)
    assert checkbox.isChecked()
    assert clicked
