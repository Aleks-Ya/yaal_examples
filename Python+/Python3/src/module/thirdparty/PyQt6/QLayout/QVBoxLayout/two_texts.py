# Window contains two text editors: one can resize only vertically
from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QTextEdit, QSizePolicy

app: QApplication = QApplication([])

text_edit_small: QTextEdit = QTextEdit()
text_edit_small.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Fixed)

text_edit_large: QTextEdit = QTextEdit()

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(text_edit_small)
layout.addWidget(text_edit_large)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
