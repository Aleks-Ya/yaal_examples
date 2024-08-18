from PyQt6.QtWidgets import QApplication, QPushButton, QVBoxLayout, QWidget

app: QApplication = QApplication([])

button: QPushButton = QPushButton('Hover over me')
button.setToolTip("Good boy")

layout: QVBoxLayout = QVBoxLayout()
layout.addWidget(button)

window: QWidget = QWidget()
window.setLayout(layout)
window.show()

app.exec()
