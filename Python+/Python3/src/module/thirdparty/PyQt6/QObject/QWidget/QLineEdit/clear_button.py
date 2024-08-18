from PyQt6.QtWidgets import QApplication, QLineEdit

app: QApplication = QApplication([])

line_edit: QLineEdit = QLineEdit('Clear me')
line_edit.setClearButtonEnabled(True)
line_edit.show()

app.exec()
