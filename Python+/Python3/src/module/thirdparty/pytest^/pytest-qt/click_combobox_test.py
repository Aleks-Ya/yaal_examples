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
    qtbot.mouseClick(combo_box, Qt.MouseButton.LeftButton)
    qtbot.keyClick(combo_box, Qt.Key.Key_Down)
    qtbot.keyClick(combo_box, Qt.Key.Key_Down)
    qtbot.keyClick(combo_box, Qt.Key.Key_Enter)
    assert combo_box.currentText() == "Three"
