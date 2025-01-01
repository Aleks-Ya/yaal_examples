from PyQt6.QtWidgets import QMessageBox
from pytestqt.qtbot import QtBot


def test_wait_exposed(qtbot: QtBot):
    message_box: QMessageBox = QMessageBox()
    assert not message_box.isVisible()
    with qtbot.waitExposed(message_box, timeout=1000):
        message_box.setWindowTitle("Important message")
        message_box.setText("It was good!")
        message_box.setIcon(QMessageBox.Icon.Warning)
        message_box.show()

    assert message_box.isVisible()
