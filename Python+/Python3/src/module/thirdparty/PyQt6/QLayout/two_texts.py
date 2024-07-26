# Window contains two text editors: one can resize only vertically
from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QTextEdit, QSizePolicy

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

text_edit_small: QTextEdit = QTextEdit()
text_edit_small.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Fixed)

text_edit_large: QTextEdit = QTextEdit()

layout.addWidget(text_edit_small)
layout.addWidget(text_edit_large)

window.setLayout(layout)

window.show()
app.exec()
