from PyQt6.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton

app: QApplication = QApplication([])

button1: QPushButton = QPushButton('Button 1')
button2: QPushButton = QPushButton('Button 2')

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(button1)
layout.addWidget(button2)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
