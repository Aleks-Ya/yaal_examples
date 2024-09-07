from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QPushButton
from pytestqt.qtbot import QtBot

clicked: bool = False


def on_button_click():
    global clicked
    clicked = True


def test_click_button(qtbot: QtBot):
    button: QPushButton = QPushButton()
    # noinspection PyUnresolvedReferences
    button.clicked.connect(on_button_click)
    qtbot.addWidget(button)
    assert not clicked
    qtbot.mouseClick(button, Qt.MouseButton.LeftButton)
    assert clicked
