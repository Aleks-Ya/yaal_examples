from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QMessageBox
from pytestqt.qtbot import QtBot

# NOT WORK
def test_wait_signal(qtbot: QtBot):
    message_box: QMessageBox = QMessageBox()
    message_box.setText("It was good!")
    message_box.show()

    qtbot.waitActive(message_box)
    qtbot.mouseClick(message_box.button(QMessageBox.StandardButton.Ok), Qt.MouseButton.LeftButton)

    with qtbot.waitSignal(message_box.finished, timeout=1000):
        pass
