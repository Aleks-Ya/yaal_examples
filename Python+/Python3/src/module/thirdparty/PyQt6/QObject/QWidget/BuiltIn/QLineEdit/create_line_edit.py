from PyQt6.QtWidgets import QApplication, QLineEdit

app: QApplication = QApplication([])

line_edit: QLineEdit = QLineEdit('Hello, PyQt6!')
line_edit.show()

app.exec()
