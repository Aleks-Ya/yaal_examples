from PyQt6.QtWidgets import QApplication, QDialog

app: QApplication = QApplication([])

dialog: QDialog = QDialog()
dialog.setWindowTitle("Close application dialog")
dialog.finished.connect(app.quit)
dialog.show()

app.exec()
