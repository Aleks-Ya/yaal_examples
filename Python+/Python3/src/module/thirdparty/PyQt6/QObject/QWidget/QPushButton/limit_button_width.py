from PyQt6.QtWidgets import QApplication, QPushButton, QWidget, QHBoxLayout

app: QApplication = QApplication([])

button: QPushButton = QPushButton('Click Me')
button.setFixedWidth(button.sizeHint().width())

layout: QHBoxLayout = QHBoxLayout()
layout.addWidget(button)

window: QWidget = QWidget()
window.setLayout(layout)
window.setFixedWidth(1000)
window.show()

app.exec()
