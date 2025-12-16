from PyQt6.QtWidgets import QMessageBox

from tests.module.thirdparty.PyQt6 import app

with app():
    message_box: QMessageBox = QMessageBox()
    message_box.setWindowTitle("Important message")
    message_box.setText("It was good!")
    message_box.setIcon(QMessageBox.Icon.Warning)
    message_box.show()
