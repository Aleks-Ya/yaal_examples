from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QTextEdit

app: QApplication = QApplication([])

text_edit: QTextEdit = QTextEdit()
text_edit.setText("abc " * 400)
text_edit.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
text_edit.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
text_edit.show()

app.exec()
