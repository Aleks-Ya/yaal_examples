from PyQt6.QtWidgets import QApplication, QMessageBox

app: QApplication = QApplication([])

QMessageBox.information(None, "My message title", "My message text")
