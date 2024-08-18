from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton, QTextEdit

app: QApplication = QApplication([])

text_edit_1: QTextEdit = QTextEdit('Text 1')
text_edit_2: QTextEdit = QTextEdit('Text 2')

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(text_edit_1)
layout.addSpacing(100)
layout.addWidget(text_edit_2)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
