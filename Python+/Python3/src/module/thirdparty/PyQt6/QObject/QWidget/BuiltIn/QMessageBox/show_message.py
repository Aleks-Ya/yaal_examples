from PyQt6.QtWidgets import QApplication, QMessageBox

app: QApplication = QApplication([])

message_box: QMessageBox = QMessageBox()
message_box.setWindowTitle("Important message")
message_box.setText("It was good!")
message_box.setIcon(QMessageBox.Icon.Warning)
message_box.show()

app.exec()
