from PyQt6.QtWidgets import QApplication, QWidget, QPushButton, QGridLayout

app: QApplication = QApplication([])

button1: QPushButton = QPushButton('Button 1')
button2: QPushButton = QPushButton('Button 2')
button3: QPushButton = QPushButton('Button 3')

layout: QGridLayout = QGridLayout()
layout.addWidget(button1, 0, 0)
layout.addWidget(button2, 0, 1)
layout.addWidget(button3, 1, 0)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
