from PyQt6.QtCore import QSize
from PyQt6.QtWidgets import QApplication, QTextEdit

app: QApplication = QApplication([])

text_edit: QTextEdit = QTextEdit()
text_edit.setText("abc " * 200)
text_edit.show()

hint: QSize = text_edit.sizeHint()
assert hint.height() == 192
assert hint.width() == 256

app.exec()
