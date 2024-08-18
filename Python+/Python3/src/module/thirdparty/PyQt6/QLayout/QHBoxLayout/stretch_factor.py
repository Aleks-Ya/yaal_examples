from PyQt6.QtWidgets import QApplication, QWidget, QHBoxLayout, QTextEdit

app: QApplication = QApplication([])

text1: QTextEdit = QTextEdit('Text 1')
text2: QTextEdit = QTextEdit('Text 2')
text3: QTextEdit = QTextEdit('Text 3')

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(text1, stretch=0)  # default
layout.addWidget(text2, stretch=1)
layout.addWidget(text3, stretch=2)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
