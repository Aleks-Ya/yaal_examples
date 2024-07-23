from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton

app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

button1: QPushButton = QPushButton('Button 1')
button2: QPushButton = QPushButton('Button 2')

layout.addWidget(button1)
layout.addWidget(button2)

window.setLayout(layout)
window.show()

app.exec()
