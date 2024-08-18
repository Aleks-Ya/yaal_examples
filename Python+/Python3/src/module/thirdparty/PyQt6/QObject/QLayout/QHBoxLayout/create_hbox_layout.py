from PyQt6.QtWidgets import QApplication, QWidget, QPushButton, QHBoxLayout

app: QApplication = QApplication([])

button1: QPushButton = QPushButton('Button 1')
button2: QPushButton = QPushButton('Button 2')
assert button1.parent() is None

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(button1)
layout.addWidget(button2)

window: QWidget = QWidget()
window.setLayout(layout)
assert button1.parent() == window
window.show()

app.exec()
