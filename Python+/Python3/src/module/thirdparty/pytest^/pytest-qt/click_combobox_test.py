from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QComboBox
from pytestqt.qtbot import QtBot


def test_select_item_in_combo_box(qtbot: QtBot):
    combo_box: QComboBox = QComboBox()
    combo_box.addItems(["One", "Two", "Three"])
    # noinspection PyUnresolvedReferences
    combo_box.show()
    qtbot.addWidget(combo_box)

    assert combo_box.currentText() == "One"
    assert combo_box.currentIndex() == 0

    qtbot.wait(1000)  # Optional waiting
    qtbot.mouseClick(combo_box, Qt.MouseButton.LeftButton)
    assert combo_box.currentText() == "One"
    assert combo_box.currentIndex() == 0

    qtbot.wait(1000)
    qtbot.keyClick(combo_box, Qt.Key.Key_Down)
    assert combo_box.currentText() == "Two"
    assert combo_box.currentIndex() == 1

    qtbot.wait(1000)
    qtbot.keyClick(combo_box, Qt.Key.Key_Down)
    assert combo_box.currentText() == "Three"
    assert combo_box.currentIndex() == 2

    qtbot.wait(1000)
    qtbot.keyClick(combo_box.view(), Qt.Key.Key_Enter)
    assert combo_box.currentText() == "Three"
    assert combo_box.currentIndex() == 2
    qtbot.wait(1000)
