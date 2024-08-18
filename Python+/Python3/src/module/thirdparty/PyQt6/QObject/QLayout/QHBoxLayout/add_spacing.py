from PyQt6.QtWidgets import QApplication, QWidget, QHBoxLayout, QTextEdit

app: QApplication = QApplication([])

text_edit_1: QTextEdit = QTextEdit('Text 1')
text_edit_2: QTextEdit = QTextEdit('Text 2')

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(text_edit_1)
layout.addSpacing(100)
layout.addWidget(text_edit_2)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
