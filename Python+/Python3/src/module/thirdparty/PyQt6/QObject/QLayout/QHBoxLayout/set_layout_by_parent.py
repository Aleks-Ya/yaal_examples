# Set layout for a widget via constructor
from PyQt6.QtWidgets import QApplication, QWidget, QPushButton, QHBoxLayout

app: QApplication = QApplication([])

button1: QPushButton = QPushButton('Button 1')
button2: QPushButton = QPushButton('Button 2')
assert button1.parent() is None

window: QWidget = QWidget()

layout: QHBoxLayout = QHBoxLayout(window)
layout.addWidget(button1)
layout.addWidget(button2)
assert button1.parent() == window

window.show()

app.exec()
