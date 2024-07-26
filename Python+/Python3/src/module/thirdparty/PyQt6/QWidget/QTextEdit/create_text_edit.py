from PyQt6.QtWidgets import QApplication, QTextEdit

app: QApplication = QApplication([])

text_edit: QTextEdit = QTextEdit()

text_edit.show()
app.exec()
