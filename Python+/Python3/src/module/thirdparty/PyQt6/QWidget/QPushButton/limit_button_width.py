from PyQt6.QtWidgets import QApplication, QPushButton, QWidget, QHBoxLayout

app: QApplication = QApplication([])
window: QWidget = QWidget()

layout: QHBoxLayout = QHBoxLayout()

button: QPushButton = QPushButton('Click Me')
button.setFixedWidth(button.sizeHint().width())
layout.addWidget(button)

window.setLayout(layout)
window.setFixedWidth(1000)

window.show()
app.exec()
