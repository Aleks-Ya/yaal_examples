import pytest
from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QComboBox, QWidget
from pytestqt.qtbot import QtBot


class VisualQtBot:
    def __init__(self, qtbot: QtBot, wait_ms: int):
        self.__qtbot: QtBot = qtbot
        self.__wait_ms: int = wait_ms

    def addWidget(self, widget: QWidget):
        self.__qtbot.addWidget(widget)
        if self.__wait_ms > 0:
            widget.show()

    def keyClick(self, widget: QWidget, key: Qt.Key):
        self.__qtbot.keyClick(widget, key)
        self.__qtbot.wait(self.__wait_ms)

    def mouseClick(self, widget: QWidget, mouse_button: Qt.MouseButton):
        self.__qtbot.mouseClick(widget, mouse_button)
        self.__qtbot.wait(self.__wait_ms)


@pytest.fixture
def visual_qtbot(qtbot: QtBot) -> VisualQtBot:
    return VisualQtBot(qtbot, 1000)


def test_select_item_in_combo_box(visual_qtbot: VisualQtBot):
    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["One", "Two", "Three"])
    # noinspection PyUnresolvedReferences
    visual_qtbot.addWidget(combo_box)

    assert combo_box.currentText() == "One"
    assert combo_box.currentIndex() == 0

    visual_qtbot.mouseClick(combo_box, Qt.MouseButton.LeftButton)
    assert combo_box.currentText() == "One"
    assert combo_box.currentIndex() == 0

    visual_qtbot.keyClick(combo_box, Qt.Key.Key_Down)
    assert combo_box.currentText() == "Two"
    assert combo_box.currentIndex() == 1

    visual_qtbot.keyClick(combo_box, Qt.Key.Key_Down)
    assert combo_box.currentText() == "Three"
    assert combo_box.currentIndex() == 2

    visual_qtbot.keyClick(combo_box.view(), Qt.Key.Key_Enter)
    assert combo_box.currentText() == "Three"
    assert combo_box.currentIndex() == 2
